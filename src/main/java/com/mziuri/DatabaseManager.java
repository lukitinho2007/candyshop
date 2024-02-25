package com.mziuri;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class DatabaseManager {
    private static final DatabaseManager instance = new DatabaseManager();
    private static final String PERSISTENCE_UNIT_NAME = "chemi-unit";

    private EntityManagerFactory emFactory;

    private DatabaseManager() {
        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }


    public List<Product> getAllProducts() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);

        List<Product> products = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.close();

        return products;
    }


    public Product getProductByName(String productName) {
        EntityManager entityManager = getEntityManager();
        Product product = entityManager.find(Product.class, productName);
        entityManager.close();

        return product;
    }


    public void updateProductQuantity(String productName, int newQuantity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, productName);
        if (product != null) {
            product.setAmount(newQuantity);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public void addProduct(Product product) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
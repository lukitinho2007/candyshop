package com.mziuri;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

@WebServlet("/store/product")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("name");
        if (productName != null) {
            Product product = getProductInfo(productName);
            if (product != null) {
                GetProductInfoResponse getProductInfoResponse = new GetProductInfoResponse();
                getProductInfoResponse.setName(product.getName());
                getProductInfoResponse.setPrice(product.getPrice());
                getProductInfoResponse.setAmount(product.getAmount());

                ObjectMapper mapper = new ObjectMapper();
                String jsonResponse = mapper.writeValueAsString(getProductInfoResponse);

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println(jsonResponse);
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Product getProductInfo(String productName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("storage.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);
        JsonNode productsNode = rootNode.get("products");

        if (productsNode != null && productsNode.isArray()) {
            Iterator<JsonNode> iterator = productsNode.elements();
            while (iterator.hasNext()) {
                JsonNode productNode = iterator.next();
                String prodName = productNode.get("prod_name").asText();
                if (prodName.equals(productName)) {
                    Product product = new Product();
                    product.setName(prodName);
                    product.setPrice((float) productNode.get("prod_price").asDouble());
                    product.setAmount(productNode.get("prod_amount").asInt());
                    return product;
                }
            }
        }

        return null;
    }
}
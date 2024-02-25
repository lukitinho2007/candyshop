package com.mziuri;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mziuri.GetProductsResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@WebServlet("/store")
public class StoreServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        List<String> productNames = readProductNamesFromStorageConfig();


        GetProductsResponse getProductsResponse = new GetProductsResponse();
        getProductsResponse.setProductNames(productNames);


        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(getProductsResponse);


        out.println(jsonResponse);
        out.flush();
    }

    private List<String> readProductNamesFromStorageConfig() throws IOException {
        List<String> productNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("storage.json");
        StorageConfig storageConfig = objectMapper.readValue(inputStream, StorageConfig.class);


        for (Product product : storageConfig.getProducts()) {
            productNames.add(product.getName());
        }

        return productNames;
    }
}
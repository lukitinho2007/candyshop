package com.mziuri;

import com.fasterxml.jackson.databind.ObjectMapper;
;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class StorageReader {
    private static final StorageReader instance = new StorageReader();

    private StorageReader() {}

    public static StorageReader getInstance() {
        return instance;
    }

    public StorageConfig readStorageConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/storage.json"));
        return objectMapper.readValue(reader, StorageConfig.class);
    }
}


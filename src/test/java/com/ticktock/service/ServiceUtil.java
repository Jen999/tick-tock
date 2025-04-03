package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ServiceUtil {
    /**
     * Private method to mimic loading test data from test resources
     * @param filename file to load
     * @return List of SessionRecord
     */
    static List<SessionRecord> loadSessionsFromTestResource(String resourceFolderName, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<SessionRecord>>() {}.getType();

        String fullPath = resourceFolderName + filename;
        Reader reader = new InputStreamReader(
                Objects.requireNonNull(
                        StorageServiceTest.class.getResourceAsStream(fullPath)
                )
        );
        return gson.fromJson(reader, listType);
    };
}

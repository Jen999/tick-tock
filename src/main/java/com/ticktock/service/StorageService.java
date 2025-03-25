package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

/**
 * Handles JSON reading and writing
 */
public class StorageService {

    private static final String FILE_PATH = "data/sessions.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves a list of session records to a JSON file.
     */
    public static void saveSessions(List<SessionRecord> sessions) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(sessions, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all saved session records from the JSON file.
     */
    public static List<SessionRecord> loadSessions() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(FILE_PATH)) {
                Type listType = new TypeToken<List<SessionRecord>>() {}.getType();
                return gson.fromJson(reader, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles JSON reading and writing
 */
public class StorageService {

    private static final Path FOLDER_PATH = Paths.get("data/");
    private static final String FILE_NAME = "sessions.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves a list of session records to a JSON file.
     */
    public static void saveSessions(List<SessionRecord> sessions, String fileName) {
        Path savePath = FOLDER_PATH.resolve(fileName);

        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            try (Writer writer = new FileWriter(savePath.toString())) {
                gson.toJson(sessions, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all saved session records from the JSON file.
     */
    public static List<SessionRecord> loadSessions(String fileName) {
        Path savePath = FOLDER_PATH.resolve(fileName);

        try {
            if (!Files.exists(Paths.get(savePath.toString()))) {
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(savePath.toString())) {
                Type listType = new TypeToken<List<SessionRecord>>() {}.getType();
                return gson.fromJson(reader, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String getFileName() {
        return FILE_NAME;
    }
}

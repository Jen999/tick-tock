package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ticktock.model.Session;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageService {
    /*
    Handles generic data storage and retrieval (file I/O)
    Abstraction of JSON operations from SessionService
     */

    private static final String FILE_PATH = "data/sessions.json";
    private static final Gson gson = new Gson();

    // Save sessions to JSON file
    public static void saveSessions(List<Session> sessions) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(sessions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load sessions from JSON file
    public static List<Session> loadSessions() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Session>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // TODO: handle storage file or folder non-existent or incorrectly formatted
}
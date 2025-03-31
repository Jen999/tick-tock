package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageServiceTest {
    private List<SessionRecord> loadedSessionRecords;
    private Gson gson;
    private Type listType;
    private SessionRecord sessionRecordOne;
    private SessionRecord sessionRecordTwo;
    private SessionRecord sessionRecordThree;
    private static final String SAVE_PATH = "data/sessionTest.json";

    @BeforeEach
    public void setUp() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        listType = new TypeToken<List<SessionRecord>>() {}.getType();
        sessionRecordOne = new SessionRecord("cs2100de", "proj", 30, "00:00:16", "00:00:11", List.of("00:00:05", "00:00:06"));
        sessionRecordTwo = new SessionRecord("cs2100", "proj", 15, "00:00:10", "00:00:05", List.of("00:00:05"));
        sessionRecordThree = new SessionRecord("cs2103", "study", 100, "00:45:00", "00:25:00", List.of("00:01:40", "00:03:20", "00:05:00", "00:06:40", "00:08:20"));
    }

    @Test
    public void loadSessions() throws Exception {
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(StorageService.class.getResourceAsStream("/StorageService/StorageServiceData.json")));
        loadedSessionRecords = gson.fromJson(reader, listType);

        List<SessionRecord> sessionRecords = new ArrayList<>();

        sessionRecords.add(sessionRecordOne);
        sessionRecords.add(sessionRecordTwo);

        // make use of reflection to get private variables
        Field[] fields = SessionRecord.class.getDeclaredFields();

        for (int i = 0; i < sessionRecords.size(); i++) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object createdVal = field.get(sessionRecords.get(i));
                Object savedVal = field.get(loadedSessionRecords.get(i));
                assertEquals(createdVal, savedVal);
            }
        }
    }

//    @Test
//    public void saveSessions() throws Exception {
//        List<SessionRecord> sessionRecords = new ArrayList<>();
//        sessionRecords.add(sessionRecordOne);
//        sessionRecords.add(sessionRecordTwo);
//        sessionRecords.add(sessionRecordThree);
//    }
}

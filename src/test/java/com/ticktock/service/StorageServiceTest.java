package com.ticktock.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageServiceTest {
    private SessionRecord sessionRecordOne;
    private SessionRecord sessionRecordTwo;
    private SessionRecord sessionRecordThree;
    private static final String RESOURCE_FOLDER = "/StorageService";

    @BeforeEach
    public void setUp() {
        sessionRecordOne = new SessionRecord("cs2100de", "proj", 30, "00:00:16", "00:00:11", List.of("00:00:05", "00:00:06"));
        sessionRecordTwo = new SessionRecord("cs2100", "proj", 15, "00:00:10", "00:00:05", List.of("00:00:05"));
        sessionRecordThree = new SessionRecord("cs2103", "study", 100, "00:45:00", "00:25:00", List.of("00:01:40", "00:03:20", "00:05:00", "00:06:40", "00:08:20"));
    }

    @Test
    public void loadSessions() throws Exception {
        List<SessionRecord>  loadedSessionRecords = loadSessionsFromTestResource("/loadSessions.json");

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

    @Test
    public void loadEmptySessions() throws Exception {
        List<SessionRecord> loadedSessionRecords = StorageService.loadSessions("/loadEmptySessions.json");
        assertEquals(new ArrayList<>(), loadedSessionRecords);
    }

    @Test
    public void saveSessions() throws Exception {
        List<SessionRecord> sessionRecords = new ArrayList<>();
        sessionRecords.add(sessionRecordOne);
        sessionRecords.add(sessionRecordTwo);
        sessionRecords.add(sessionRecordThree);

        // save the test records
        StorageService.saveSessions(sessionRecords, "saveSessionsActual.json");

        // load the test records
        List<SessionRecord> actualSaveData = StorageService.loadSessions("saveSessionsActual.json");
        List<SessionRecord> expectedSaveData = loadSessionsFromTestResource("/saveSessionsExpected.json");

        // assert that they have the same size
        assertEquals(actualSaveData.size(), expectedSaveData.size());

        Field[] fields = SessionRecord.class.getDeclaredFields();
        for (int i = 0; i < sessionRecords.size(); i++) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object createdVal = field.get(actualSaveData.get(i));
                Object savedVal = field.get(expectedSaveData.get(i));
                assertEquals(createdVal, savedVal);
            }
        }

        // remove the test records to clean up
        Paths.get("data/saveSessionsActual.json").toFile().deleteOnExit();
    }

    /**
     * Private method to mimic loading test data from test resources
     * @param filename file to load
     * @return List of SessionRecord
     */
    private List<SessionRecord> loadSessionsFromTestResource(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<SessionRecord>>() {}.getType();

        String fullPath = RESOURCE_FOLDER + filename;
        Reader reader = new InputStreamReader(
                Objects.requireNonNull(
                        StorageServiceTest.class.getResourceAsStream(fullPath)
                )
        );
        return gson.fromJson(reader, listType);
    };
}

package com.ticktock.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionServiceTest {
    private static final String RESOURCE_FILE_NAME = "/sessionServiceExpected.json";
    private static final String RESOURCE_FOLDER_NAME = "/service/SessionService";
    private static final String SAVE_FILE_NAME = "actualSessionService.json";

    @Test
    public void createAndSaveSession() throws IllegalAccessException {
        SessionService.createAndSaveSession(
                "cs2100",
                "proj",
                100,
                "00:20:30",
                "00:10:00",
                List.of("00:02:30", "00:05:25", "00:02:05"),
                SAVE_FILE_NAME
        );

        List<SessionRecord> expectedSessionRecords = ServiceUtil.loadSessionsFromTestResource(RESOURCE_FOLDER_NAME, RESOURCE_FILE_NAME);
        List<SessionRecord> actualSessionRecords = StorageService.loadSessions(SAVE_FILE_NAME);

        // ensures same number of records
        assertEquals(expectedSessionRecords.size(), actualSessionRecords.size());

        for (int i = 0; i < expectedSessionRecords.size(); i++) {
            assertTrue(ServiceUtil.isSessionRecordEqual(expectedSessionRecords.get(i), actualSessionRecords.get(i)));
        }

        // Ensures file saved is deleted
        Paths.get("data" + File.separator + SAVE_FILE_NAME).toFile().deleteOnExit();
    }
}

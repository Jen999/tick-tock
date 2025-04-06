package com.ticktock.model.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleTest {
    private static Module moduleOne;
    private static Module moduleTwo;
    private static Module moduleThree;
    private static Module moduleFour;
    private static String moduleFive;
    private static Module moduleSix;
    private static int moduleSeven;

    @BeforeAll
    public static void setUp() {
        moduleOne = new Module("CS2103");
        moduleTwo = new Module("cs2103");
        moduleThree = new Module("");
        moduleFour = new Module("CS2103");
        moduleFive = "CS2103";
        moduleSix = new Module("12345");
        moduleSeven = 12345;
    }

    @Test
    public void getModuleName() {
        assertEquals("CS2103", moduleOne.getModuleName());
        assertEquals("cs2103", moduleTwo.getModuleName());
        assertEquals("", moduleThree.getModuleName());
    }

    @Test
    public void equals() {
        // Module to module comparisons
        assertNotEquals(moduleOne, moduleTwo);
        assertNotEquals(moduleOne, moduleThree);
        assertNotEquals(moduleTwo, moduleThree);
        assertEquals(moduleOne, moduleFour);

        // Unable to use assertEquals and assertNonEquals here, not sure why it does not trigger the correct equality comparisons
        // Module to string comparisons
        assertTrue(moduleOne.equals(moduleFive));
        assertFalse(moduleTwo.equals(moduleFive));

        // Module to non string comparisons
        assertFalse(moduleSix.equals(moduleSeven));
    }
}

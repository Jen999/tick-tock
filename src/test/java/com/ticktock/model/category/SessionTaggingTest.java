package com.ticktock.model.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTaggingTest {

    private SessionTagging sessionTagging;
    private SessionTagging customSessionTagging;

    @BeforeEach
    void setUp() {
        sessionTagging = new SessionTagging("Module1", DefaultSessionCategory.STUDY);
        customSessionTagging = new SessionTagging("Module2", "project");
    }

    @Test
    void testGetModuleName() {
        // check that module name is correctly returned
        assertEquals("Module1", sessionTagging.getModuleName());
    }

    @Test
    void testAddCategory_PredefinedCategory() {
        // adding a predefined category for both constructors
        boolean result = sessionTagging.addCategory(DefaultSessionCategory.QUIZ_PREPARATION);
        assertTrue(result, "Category should be added");
        assertTrue(sessionTagging.getCategories().contains("QUIZ_PREPARATION"), "Category should be in the set");
    }

    @Test
    void testAddCategory_CustomCategory() {
        // adding a custom category
        boolean result = sessionTagging.addCategory("CustomCategory");
        assertTrue(result, "Custom category should be added");
        assertTrue(sessionTagging.getCategories().contains("CustomCategory"), "Custom category should be in the set");
    }

    @Test
    void testAddCategory_Duplicate() {
        // adding a duplicate category
        boolean result = sessionTagging.addCategory(DefaultSessionCategory.STUDY);
        assertFalse(result, "Duplicate category should not be added");
    }

    @Test
    void testRemoveCategory() {
        // removing a category
        boolean result = sessionTagging.removeCategory("NON_EXISTENT_CATEGORY");
        assertFalse(result, "Non-existing category should not be removed");

        sessionTagging.addCategory("Tutorial"); // Add a custom category first
        result = sessionTagging.removeCategory("Tutorial");
        assertTrue(result, "Category should be removed");
        assertFalse(sessionTagging.getCategories().contains("Tutorial"), "Category should be removed from the set");
    }

    @Test
    void testGetCategories() {
        // getting categories
        Set<String> categories = sessionTagging.getCategories();
        assertNotNull(categories, "Categories set should not be null");
        assertTrue(categories.contains("STUDY"), "Categories should contain the default 'STUDY' category");

        Set<String> categories2 = customSessionTagging.getCategories();
        assertNotNull(categories2, "Categories set should not be null");
        assertTrue(categories2.contains("project"), "Categories should contain the 'project' category");
    }

    @Test
    void testToString() {
        // toString method
        String expectedString = "Module: Module1, Categories: [STUDY]";
        assertEquals(expectedString, sessionTagging.toString(), "toString should return correct string");
    }

    @Test
    void testSetModule() {
        sessionTagging.setModule("Module2");
        assertEquals("Module2", sessionTagging.getModuleName(), "Module name should be correct");
    }
}



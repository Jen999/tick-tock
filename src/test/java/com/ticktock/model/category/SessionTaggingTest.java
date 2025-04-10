package com.ticktock.model.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTaggingTest {
    private static SessionTagging sessionTaggingOne;
    private static SessionTagging sessionTaggingTwo;
    private static String customCategory;
    private static String moduleName;

    @BeforeAll
    public static void setUp() {
        // Initialize SessionTagging objects for testing
        sessionTaggingOne = new SessionTagging("CS2103", DefaultSessionCategory.STUDY);
        sessionTaggingTwo = new SessionTagging("CS2104", "CustomCategory");

        // Initialize simple String values for testing
        customCategory = "CustomCategory";
        moduleName = "CS2103";
    }

    @Test
    public void getModuleName() {
        // Test retrieving the module name
        assertEquals("CS2103", sessionTaggingOne.getModuleName());
        assertEquals("CS2104", sessionTaggingTwo.getModuleName());
    }

    @Test
    public void setModule() {
        // Test changing the module name
        sessionTaggingOne.setModule("CS2105");
        assertEquals("CS2105", sessionTaggingOne.getModuleName());

        // Test that categories are not modified when module name is updated
        assertTrue(sessionTaggingOne.getCategories().contains(DefaultSessionCategory.STUDY.name()));
    }

    @Test
    public void addCategoryWithPredefinedCategory() {
        // Test adding predefined categories
        assertTrue(sessionTaggingOne.addCategory(DefaultSessionCategory.QUIZ_PREPARATION));
        assertTrue(sessionTaggingOne.getCategories().contains(DefaultSessionCategory.QUIZ_PREPARATION.name()));

        // Adding duplicate category should return false
        assertFalse(sessionTaggingOne.addCategory(DefaultSessionCategory.QUIZ_PREPARATION));
    }

    @Test
    public void addCategoryWithCustomCategory() {
        // Test adding custom categories
        assertTrue(sessionTaggingTwo.addCategory(customCategory));
        assertTrue(sessionTaggingTwo.getCategories().contains(customCategory));

        // Adding duplicate custom category should return false
        assertFalse(sessionTaggingTwo.addCategory(customCategory));
    }

    @Test
    public void removeCategory() {
        // Test removing categories
        assertTrue(sessionTaggingOne.removeCategory(DefaultSessionCategory.STUDY.name()));
        assertFalse(sessionTaggingOne.getCategories().contains(DefaultSessionCategory.STUDY.name()));

        // Trying to remove a non-existing category should return false
        assertFalse(sessionTaggingOne.removeCategory("NonExistentCategory"));
    }

    @Test
    public void getCategories() {
        // Test getting all categories
        Set<String> categories = sessionTaggingOne.getCategories();
        assertTrue(categories.contains(DefaultSessionCategory.STUDY.name()));
    }

    @Test
    public void toStringTest() {
        // Test the string representation of the session tagging
        String expected = "Module: CS2103, Categories: [STUDY]";
        assertEquals(expected, sessionTaggingOne.toString());
    }

    @Test
    public void removeCategoryWithNull() {
        // Test removing a null category
        assertFalse(sessionTaggingOne.removeCategory(null));
    }

    @Test
    public void finalizeCategories() {
        // Test making categories immutable after initialization
        sessionTaggingOne.finalizeCategories();

        // Try to add a category after finalizing categories, should return false
        assertFalse(sessionTaggingOne.addCategory("NewCategory"));
    }
}


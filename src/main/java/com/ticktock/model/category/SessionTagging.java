package com.ticktock.model.category;

import java.util.HashSet;
import java.util.Set;

/**
 * This class holds the Module and Category information for a Session object.
 * Each Session object is expected to have exactly one Module but multiple Categories.
 */
public class SessionTagging {
    private Module module;
    private Set<String> categories;

    /**
     * Constructor for predefined category.
     * @param moduleName Name of the module.
     * @param defaultCategory Default category.
     */
    public SessionTagging(String moduleName, DefaultSessionCategory defaultCategory) {
        this.module = new Module(moduleName);
        this.categories = new HashSet<>();
        this.categories.add(defaultCategory.name());
    }

    /**
     * Constructor for custom category.
     * @param moduleName Name of the module.
     * @param customCategory Custom category name.
     */
    public SessionTagging(String moduleName, String customCategory) {
        this.module = new Module(moduleName);
        this.categories = new HashSet<>();
        this.categories.add(customCategory);
    }

    /**
     * Gets the module name.
     * @return Module name as string.
     */
    public String getModuleName() {
        return module.getModuleName();
    }

    /**
     * Replace the current module with a new one.
     * @param moduleName New module name.
     */
    public void setModule(String moduleName) {
        this.module = new Module(moduleName);
    }

    /**
     * Adds a predefined category.
     * @param category Default session category.
     * @return true if added, false if already exists.
     */
    public boolean addCategory(DefaultSessionCategory category) {
        return categories.add(category.name());
    }

    /**
     * Adds a custom user-defined category.
     * @param category Custom category name.
     * @return true if added, false if already exists.
     */
    public boolean addCategory(String category) {
        return categories.add(category);
    }

    /**
     * Removes a category.
     * @param category Category name to remove.
     * @return true if removed, false if not found.
     */
    public boolean removeCategory(String category) {
        return categories.remove(category);
    }

    /**
     * Gets all categories.
     * @return Set of category names.
     */
    public Set<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Module: " + module.getModuleName() + ", Categories: " + categories;
    }
}
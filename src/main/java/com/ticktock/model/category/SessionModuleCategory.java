package com.ticktock.model.category;

/**
 * This class holds the Module information for the Session object
 * Each Session object is expected to only have 1 Module tagged to it
 */
public class SessionModuleCategory {
    private Module module;

    /**
     * Constructor
     * @param moduleName Name of the module
     */
    public SessionModuleCategory(String moduleName) {
        this.module = new Module(moduleName);
    }

    /**
     * Gets the module name of the tagged to the Session
     * @return String information of the module
     */
    public String getModuleName() {
        return module.getModuleName();
    }

    /**
     * Replace the current Module with a new Module with moduleName
     * @param moduleName New module name
     */
    public void setModule(String moduleName) {
        this.module = new Module(moduleName);
    }
}

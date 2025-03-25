package com.ticktock.model.category;
/**
 * Module holds the information about the module the user is studying for
 */
class Module {
    private String moduleName;
    /**
     * Constructor
     * @param moduleName String that represents the moduleName
     */
    public Module(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Getter for moduleName
     * @return moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * This method returns True if another module has the same moduleName as the current module
     * @param obj to be compared to
     * @return true if object is a Module and has the same moduleName | true if object is a string and has the same moduleName
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Module) {
            return this.moduleName.equals(((Module) obj).moduleName);
        } else if (obj instanceof String) {
            return this.moduleName.equals(obj);
        }
        return false;
    }
}

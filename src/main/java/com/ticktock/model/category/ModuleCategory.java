package com.ticktock.model.category;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class ModuleCategory {
    public static final List<String> MODULE_NAMES = new ArrayList<>();

    public void loadModuleNames(String resourceName) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(ModuleCategory.class.getClassLoader().getResourceAsStream(resourceName)
                        )
                )
        );

        MODULE_NAMES.addAll(br.lines().toList());
    }
}

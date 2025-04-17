package com.ticktock.controller;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainControllerTest {

    @Test
    public void testFormatSecondsUsingReflection() throws Exception {
        Method method = MainController.class.getDeclaredMethod("formatSeconds", long.class);
        method.setAccessible(true); // bypass the private access
        MainController controller = new MainController();

        String result = (String) method.invoke(controller, 125L);
        assertEquals("00:02:05", result);

        String result2 = (String) method.invoke(controller, 126L);
        assertEquals("00:02:06", result2);

        String result3 = (String) method.invoke(controller, 3600L);
        assertEquals("01:00:00", result3);

        String result4 = (String) method.invoke(controller, 2700L);
        assertEquals("00:45:00", result4);
    }
}



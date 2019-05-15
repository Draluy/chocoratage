package fr.raluy.chocoratage;

import java.util.Arrays;
import java.util.List;

public class Config {

    private static boolean debugMode;
    private static boolean testSessionLocking;

    public static void parse(String[] args) {
        List<String> argsList = Arrays.asList(args);
        debugMode = argsList.contains("debug");
        testSessionLocking = argsList.contains("lock");
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isTestSessionLocking() {
        return testSessionLocking;
    }
}

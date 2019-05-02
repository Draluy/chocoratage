package fr.raluy.chocoratage;

import java.util.Arrays;

public class Config {

    private static boolean debugMode;
    private static boolean testSessionLocking;

    public static void parse(String[] args) {
        debugMode = Arrays.stream(args).anyMatch(s -> s.equals("debug"));
        testSessionLocking = Arrays.stream(args).anyMatch(s -> s.equals("lock"));
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isTestSessionLocking() {
        return testSessionLocking;
    }
}

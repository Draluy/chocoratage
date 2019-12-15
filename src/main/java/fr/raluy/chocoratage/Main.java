package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String... args) throws IOException {
        Config.parse(args);

        configureJNativeHookLogger();

        KeyLogger keyLogger = new KeyLogger();
        Os os = Optional.ofNullable(Config.getForcedOs()).orElseGet(() -> Os.guess());
        SessionLocker sessionLocker = new SessionLocker(os);
        keyLogger.addListener(sessionLocker);

        if(Config.isTestSessionLocking()) {
            sessionLocker.run();
        }
    }

    private static void configureJNativeHookLogger() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }
}

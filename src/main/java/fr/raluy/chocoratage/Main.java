package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Config.parse(args);

        configureJNativeHookLogger();

        KeyLogger keyLogger = new KeyLogger();
        SessionLocker sessionLocker = new SessionLocker();
        keyLogger.onChocoblastage(sessionLocker);

        if(Config.isTestSessionLocking()) {
            sessionLocker.run();
        }
    }

    private static void configureJNativeHookLogger() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }
}

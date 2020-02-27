package fr.raluy.chocoratage;

import fr.raluy.chocoratage.locking.SessionLocker;
import org.jnativehook.GlobalScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws IOException, URISyntaxException {
        configureJNativeHookLogger();

        Config.parse(args);

        if (Config.isDebugMode()) {
            displayOsInfo();
        }

        KeyLogger keyLogger = new KeyLogger();
        Os os = Optional.ofNullable(Config.getForcedOs()).orElseGet(() -> Os.guess());
        SessionLocker sessionLocker = new SessionLocker(os);
        keyLogger.addListener(sessionLocker);

        if (Config.isDebugMode()) {
            log.info("OS set to {}", os);
            log.info("Forbidden phrases:");
            Config.getForbiddenPhrases().forEach(fph -> log.info("* {}", fph.getPhraseLower()));
        }

        if(Config.isTestSessionLocking()) {
            sessionLocker.run();
        }
    }

    private static void displayOsInfo() {
        for(String info : Os.getOsInfos()) {
            log.info(info);
        }
    }

    private static void configureJNativeHookLogger() {
        java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName())
            .setLevel(Level.OFF);
    }
}

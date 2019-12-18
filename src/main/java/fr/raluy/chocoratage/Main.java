package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws IOException {
        Config.parse(args);

        displayOsInfo();
        configureJNativeHookLogger();

        KeyLogger keyLogger = new KeyLogger();
        Os os = Optional.ofNullable(Config.getForcedOs()).orElseGet(() -> Os.guess());
        if (Config.isDebugMode()) {
            log.info("OS set to {}", os);
        }
        SessionLocker sessionLocker = new SessionLocker(os);
        keyLogger.addListener(sessionLocker);

        if(Config.isTestSessionLocking()) {
            sessionLocker.run();
        }
    }

    private static void displayOsInfo() {
        log.info("OS Type: {}", Os.getOsType());
        log.info("OS Name: {}", Os.getOsName());
        log.info("OS Version: {}", Os.getOsVersion());
        log.info("OS Version ID: {}", Os.getOsVersionId());
        log.info("OS Arch: {}", Os.getOsArch());
        log.info("Desktop Env: {}", Os.getDesktopEnvironment());
        log.info("GDM Session: {}", Os.getGdmSession());
    }

    private static void configureJNativeHookLogger() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }
}

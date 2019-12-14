package fr.raluy.chocoratage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

public enum Os {
    LINUX("linux") {
        /**
         * Control+Alt+L
         */
        public void lockSession() {
            //TODO dbus-send --type=method_call --dest=org.gnome.ScreenSaver /org/gnome/ScreenSaver org.gnome.ScreenSaver.Lock
            //TODO $!(sleep 10s ;  xset dpms force suspend) & xdg-screensaver lock
            //TODO xdg-screensaver lock
            robotTyping(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_L);
        }
    },

    WINDOWS("windows") {
        /**
         * Windows+L
         */
        public void lockSession() {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(System.getenv("windir") + "\\System32\\rundll32.exe", "user32.dll,LockWorkStation");
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    },

    MAC("mac", "darwin", "rhapsody") { // Rhapsody is the Power PC Mac name. Not sure there's many left but sure as hell they need to be chocoblast-proof. TODO not sure "darwin" is a valid name.

        /**
         * Control+Shift+Power or Control+Shift+Escape for older MacBooks with an optical drive
         */
        @Override
        public void lockSession() {
            robotTyping(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE);
        }
    },
    FREEBSD("freebsd") {
        //TODO xlock -mode qix from http://www.freshports.org/x11/xlockmore
    },
    SOLARIS("solaris", "sun"),
    AIX("aix"), // openjdk still supports it
    OTHER_UNIX("hp-ux", "irix", "digital", "compaq"), // Not running any JVM, unless dockerized maybe
    UNKNOWN;


    private String[] names;


    Os(String... names) {
        this.names = stream(names).filter(Objects::nonNull).map(String::toLowerCase).toArray(String[]::new);
    }

    private boolean matches(String name) {
        if(name == null) {
            return false;
        } else {
            String lowName = name.toLowerCase();
            return stream(names).anyMatch(n -> lowName.startsWith(n));
        }
    }

    public void lockSession() {
        throw new UnsupportedOperationException(name());
    }


    void robotTyping(int... lockingKeys) {
        try {
            Robot robot = new Robot();
            IntStream.of(lockingKeys).forEach(robot::keyPress);

            Thread.sleep(100L);

            IntStream.of(lockingKeys).forEach(robot::keyRelease);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static String getOsArch() {
        return System.getProperty("os.arch"); // eg: amd64
    }

    public static Os guess() {
        String osName = getOsName();
        return stream(values()).filter(os -> os.matches(osName)).findAny().orElse(UNKNOWN);
    }
}

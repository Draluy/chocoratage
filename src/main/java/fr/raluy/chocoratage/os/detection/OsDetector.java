package fr.raluy.chocoratage.os.detection;

public class OsDetector {

    private OsDetector() {
    }

    public static String getOsName() {
        return System.getProperty("os.name", "unknown");
    }

    public static Os platform() {
        String osname = System.getProperty("os.name", "generic").toLowerCase();
        if (osname.startsWith("windows")) {
            return Os.WINDOWS;
        }
        else if (osname.startsWith("linux")) {
            return Os.LINUX;
        }
        else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
            return Os.MAC;
        }
        else return Os.UNKNOWN;
    }
}

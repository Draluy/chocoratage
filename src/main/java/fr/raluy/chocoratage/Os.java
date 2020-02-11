package fr.raluy.chocoratage;

import fr.raluy.chocoratage.locking.LockMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fr.raluy.chocoratage.ProcessLauncher.runProcessAndOutput;
import static fr.raluy.chocoratage.Utils.*;
import static java.util.Arrays.stream;

public enum Os {
    LINUX("linux") {
        @Override
        public boolean isLockMethodKnown() {
            return false;
        }
    },
    WINDOWS("windows") {
        @Override
        public boolean isLockMethodKnown() {
            return true;
        }
        @Override
        public LockMethod getDefaultLockMethod() {
            return LockMethod.WINDOWS;
        }
    },
    OSX("mac", "darwin", "rhapsody") {
        @Override
        public boolean isLockMethodKnown() {
            return true;
        }
        @Override
        public LockMethod getDefaultLockMethod() {
            return LockMethod.OSX;
        }
    }, // Rhapsody is the Power PC Mac name. Not sure there's many left but sure as hell they need to be chocoblast-proof. TODO not sure "darwin" is a valid name.
    FREEBSD("freebsd") {
        @Override
        public boolean isLockMethodKnown() {
            return true;
        }
        @Override
        public LockMethod getDefaultLockMethod() {
            return LockMethod.GNOME_SESSION_QUIT;
        }
    },
    SOLARIS("solaris", "sunos", "sun") {
        @Override
        public boolean isLockMethodKnown() {
            return true;
        }
        @Override
        public LockMethod getDefaultLockMethod() {
            return LockMethod.XLOCK;
        }
    }, // Not sure "sun" is a valid value
    AIX("aix"), // openjdk still supports it TODO
    OTHER_UNIX("hp-ux", "irix", "digital", "compaq"), // Not running any JVM, unless dockerized maybe, which will obviously not allow locking of the host
    UNKNOWN;

    private static final String WINDOWS_NAME = "Windows";
    public static final String ENV_DESKTOP = "XDG_CURRENT_DESKTOP";
    public static final String ENV_SESSION = "GDMSESSION";

    private final String[] names;


    Os(String... names) {
        this.names = stream(names).filter(Objects::nonNull).map(String::toLowerCase).toArray(String[]::new);
    }

    public String[] getNames() {
        return names;
    }

    public boolean isLockMethodKnown() {
        return false;
    }

    public LockMethod getDefaultLockMethod() {
        throw new UnsupportedOperationException("Lock Method unknown.");
    }


    /**
     * @return "Linux", "FreeBSD", "Mac OSX", "SunOS", "Windows <version>"...
     */
    public static String getOsType() {
        String osType = System.getProperty("os.name");
        if(WINDOWS.matches(osType)) {
            osType = WINDOWS_NAME;
        }
        return osType;
    }

    /**
     * @return the OS name
     * @see http://0pointer.de/blog/projects/os-release.html
     * @see https://www.freedesktop.org/software/systemd/man/os-release.html
     */
    public static String getOsName() {
        String osType = getOsType();
        if (LINUX.matches(osType)) {
            return or(getValueForKey("NAME", "=", runProcessAndOutput("cat", "/etc/os-release")), // eg: NAME="Ubuntu"
                    () -> getValueAfter(":", getFirst(runProcessAndOutput("lsb_release", "-i"))), //eg: Distributor ID:	Ubuntu
                    () -> getValueBefore("release", getFirst(runProcessAndOutput("cat", "/etc/fedora-release"))), // eg: Fedora release 31 (Thirty One)
                    () -> getFirstOptional(runProcessAndOutput("uname", "-n")))
                    .orElse(osType);
        } else {
            return osType;
        }
    }

    public static String getOsVersionId() {
        String osType = getOsType();
        if (LINUX.matches(osType)) {
            return or(getValueForKey("VERSION_ID", "=", runProcessAndOutput("cat", "/etc/os-release")), // eg: VERSION_ID="18.04"
                    () -> getValueAfter(":", getFirst(runProcessAndOutput("lsb_release", "-r"))), // eg: Release:	18.04
                    () -> getValueAfter("release", getFirst(runProcessAndOutput("cat", "/etc/fedora-release"))), // eg: Fedora release 31 (Thirty One)
                    () -> getFirstOptional(runProcessAndOutput("uname", "-r")))
                    .orElse(osType);
        } else {
            return getOsVersion();
        }
    }

    /**
     * Not very useful, will probably return the kernel version like 4.15.0-72-generic
     * @return
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * eg: amd64
     * @return
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * For Linux
     * XDG_CURRENT_DESKTOP - Tells you what desktop environment you are using
     * @see https://askubuntu.com/questions/72549/how-to-determine-which-window-manager-is-running
     */
    public static String getDesktopEnvironment() {
        return System.getenv(ENV_DESKTOP);
    }

    /**
     * For Linux
     * GDMSESSION - Tells you what option you selected from the lightdm greeter to login.
     * @see https://askubuntu.com/questions/72549/how-to-determine-which-window-manager-is-running
     */
    public static String getGdmSession() {
        return System.getenv(ENV_SESSION);
    }


    public static Os guess() {
        String osName = getOsType();
        return stream(values()).filter(os -> os.matches(osName)).findAny().orElse(UNKNOWN);
    }

    private boolean matches(String name) {
        if(name == null) {
            return false;
        } else {
            String lowName = name.toLowerCase();
            return stream(names).anyMatch(n -> lowName.startsWith(n));
        }
    }

}

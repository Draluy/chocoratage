package fr.raluy.chocoratage.os.detection;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public enum Os {
    LINUX(Os::lockLinuxKdeSession),
    WINDOWS(Os::lockWindowsSession),
    MAC(() -> {}), //TODO: add keys for mac
    UNKNOWN(() -> {});

    private Runnable lockFunction;

    Os(Runnable lockFunction) {
        this.lockFunction = lockFunction;
    }

    public static void lockLinuxKdeSession() {
        List<Integer> lockingKeys = Arrays.asList(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_L);
        try {
            Robot robot = new Robot();
            lockingKeys.forEach(key -> robot.keyPress(key));

            Thread.sleep(100);

            lockingKeys.forEach(key -> robot.keyRelease(key));
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void lockWindowsSession() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("%windir%\\System32\\rundll32.exe user32.dll,LockWorkStation");
            pr.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Runnable getLockFunction() {
        return lockFunction;
    }
}

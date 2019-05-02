package fr.raluy.chocoratage.os.detection;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public enum Os {
    LINUX(Arrays.asList(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_L)),
    WINDOWS(Arrays.asList(KeyEvent.VK_WINDOWS, KeyEvent.VK_L)),
    MAC(Collections.emptyList()), //TODO: add keys for mac
    UNKNOWN(Collections.emptyList());

    private List<Integer> lockingKeys;

    Os(List<Integer> lockingKeys) {
        this.lockingKeys = lockingKeys;
    }

    public void lockSession() {
        try {
            Robot robot = new Robot();
            lockingKeys.forEach(key -> robot.keyPress(key));

            Thread.sleep(500);

            lockingKeys.forEach(key -> robot.keyRelease(key));
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getLockingKeys() {
        return lockingKeys;
    }
}

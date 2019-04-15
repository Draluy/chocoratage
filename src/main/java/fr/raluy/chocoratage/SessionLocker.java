package fr.raluy.chocoratage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SessionLocker implements Runnable {

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_L);

            Thread.sleep(100);

            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

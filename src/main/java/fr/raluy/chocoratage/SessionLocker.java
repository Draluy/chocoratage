package fr.raluy.chocoratage;

import fr.raluy.chocoratage.os.detection.Os;
import fr.raluy.chocoratage.os.detection.OsDetector;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class SessionLocker implements Runnable {

    @Override
    public void run() {
        Os platform = OsDetector.platform();

        if (Config.isDebugMode()) {
            System.out.println(platform.name() + " detected. Session locked.");
        }

        platform.getLockFunction().run();
    }
}

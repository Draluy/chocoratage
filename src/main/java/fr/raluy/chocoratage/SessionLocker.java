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
            System.out.println("Keys pressed = " + serializeIntegers(platform.getLockingKeys()));
        }

        platform.lockSession();
    }

    private String serializeIntegers(List<Integer> ints) {
        return ints.stream().map(i -> KeyEvent.getKeyText(i)).collect(Collectors.joining("+"));
    }
}

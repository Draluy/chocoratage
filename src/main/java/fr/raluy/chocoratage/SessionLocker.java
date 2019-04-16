package fr.raluy.chocoratage;

import fr.raluy.chocoratage.os.detection.OsDetector;

public class SessionLocker implements Runnable {

    @Override
    public void run() {
        OsDetector.platform().lockSession();
    }

}

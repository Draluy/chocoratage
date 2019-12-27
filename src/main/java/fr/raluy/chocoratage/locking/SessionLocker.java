package fr.raluy.chocoratage.locking;

import fr.raluy.chocoratage.Config;
import fr.raluy.chocoratage.Os;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

public class SessionLocker implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SessionLocker.class);

    private LockMethod lockMethod;

    public SessionLocker(LockMethod lockMethod) {
        this.lockMethod = requireNonNull(lockMethod, "Lock method must be specified.");
        if(lockMethod == LockMethod.OSX) {
            log.info("Caution with OSX: for the lock screen to actually protect you, go to the \"Security & Privacy\" settings and select \"Require password *immediately* after sleep or screensaver begins\"");
        }

        if (Config.isDebugMode()) {
            log.info("Lock method set to {}", this.lockMethod.name());
        }
    }

    public SessionLocker(Os os) {
        this(LockMethod.guess(os));
    }

    public static SessionLocker guess() {
        if (Config.isDebugMode()) {
            log.info("Guessing OS and Lock method");
        }
        return new SessionLocker(Os.guess());
    }

    @Override
    public void run() {
        if (Config.isDebugMode()) {
            log.info("Triggering lock method {}", lockMethod.name());
        }

        if(Config.isSimulation()) {
            log.info("lockSession() simulation!");
        } else {
            lockMethod.lock();
        }
    }
}

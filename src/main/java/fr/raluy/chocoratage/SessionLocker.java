package fr.raluy.chocoratage;

import static java.util.Objects.requireNonNull;

public class SessionLocker implements Runnable {

    private Os os;

    public SessionLocker() {
        this(Os.guess());
        if (Config.isDebugMode()) {
            System.out.println("Guessing OS");
        }
    }

    public SessionLocker(Os os) {
        this.os = requireNonNull(os, "OS must be specified.");
        if (Config.isDebugMode()) {
            System.out.printf("%s configured for %s\n", getClass().getSimpleName(), this.os.name());
        }
    }

    @Override
    public void run() {
        if (Config.isDebugMode()) {
            System.out.printf("Running %s for %s\n", getClass().getSimpleName(), os.name());
        }

        if(Config.isSimulation()) {
            System.out.println("lockSession() simulation!");
        } else {
            os.lockSession();
        }
    }
}

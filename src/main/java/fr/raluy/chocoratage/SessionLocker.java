package fr.raluy.chocoratage;

public class SessionLocker implements Runnable {

    @Override
    public void run() {
        Os os = Os.guess();

        if (Config.isDebugMode()) {
            System.out.println(os.name() + " detected. Session locked.");
        }

        os.lockSession();
    }
}

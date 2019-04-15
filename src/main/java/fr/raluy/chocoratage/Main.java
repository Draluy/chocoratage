package fr.raluy.chocoratage;

public class Main {
    public static void main(String[] args) {
        KeyLogger keyLogger = new KeyLogger();
        SessionLocker sessionLocker = new SessionLocker();
        keyLogger.onChocoblastage(sessionLocker);
    }
}

package fr.raluy.chocoratage;

public class ChocoratageException extends RuntimeException {
    public ChocoratageException(String message) {
        super(message);
    }

    public ChocoratageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChocoratageException(Exception e) {
        super (e);
    }
}

package fr.raluy.chocoratage;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.List;

public class CircularBuffer {
    private String buffer = "";
    private int MAX_SIZE = 100;

    /**
     * Adds a char at the end of the string, and evicts first chars of the string to
     * reduce the string to a maximum of MAX_SIZE characters
     *
     * @param keyEvent the char to append to the end
     */
    public void add(NativeKeyEvent keyEvent) {
        if (keyEvent == null ){
            return;
        }

        if (keyEvent.equals(NativeKeyEvent.VC_DELETE)) {
            buffer = buffer.substring(0, buffer.length() - 1);
        } else {
            buffer = buffer + keyEvent.getKeyText(keyEvent.getKeyCode());
        }

        if (buffer.length() > MAX_SIZE) {
            //cut the buffer back to MAX_SIZE chars
            buffer = buffer.substring(buffer.length() - MAX_SIZE, buffer.length());
        }
    }

    public boolean containsUppercase(List<String> strings) {
        return strings.stream()
                .anyMatch(s -> buffer.contains(s.toUpperCase()));
    }

    public void clear() {
        buffer = "";
    }

    @Override
    public String toString() {
        return buffer;
    }
}

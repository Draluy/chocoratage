package fr.raluy.chocoratage;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CircularBuffer {
    private String current = "";
    private String previous = "";
    private int MAX_SIZE = 100;

    /**
     * Adds a char at the end of the string, and evicts first chars of the string to
     * reduce the string to a maximum of MAX_SIZE characters
     *
     * @param keyEvent the char to append to the end
     */
    public void add(NativeKeyEvent keyEvent) {
        if (keyEvent == null) {
            return;
        }

        if (isALetter(keyEvent)) {
            String keyText = keyEvent.getKeyText(keyEvent.getKeyCode());
            current = current + keyText;
        } else {
            if (!current.isEmpty()) {
                previous = current;
                current = "";
            }
        }

        if (current.length() > MAX_SIZE) {
            //cut the buffer back to MAX_SIZE chars
            current = current.substring(current.length() - MAX_SIZE, current.length());
        }
    }

    private boolean isALetter(NativeKeyEvent keyEvent) {
        if (keyEvent.isActionKey()) {
            return false;
        }

        String keyText = keyEvent.getKeyText(keyEvent.getKeyCode());

        if (keyText.length() > 1) {
            return false;
        } else if (keyText.matches("[a-zA-Z0-9]+")) {
            return true;
        }
        return false;
    }

    public boolean containsUppercase(List<String> strings) {
        return strings.stream()
                .anyMatch(s -> Levenshtein.isThisSimilarEnoughToThat(s.toUpperCase(), previous)
                        || Levenshtein.isThisSimilarEnoughToThat(s.toUpperCase(), current));
    }

    public void clear() {
        previous = "";
        current = "";
    }

    @Override
    public String toString() {
        return Stream.of(previous, current)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
}

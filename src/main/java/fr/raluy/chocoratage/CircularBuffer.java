package fr.raluy.chocoratage;

import java.util.List;

public class CircularBuffer {
    private String buffer = "";
    private int MAX_SIZE = 100;

    /**
     * Adds a char at the end of the string, and evicts first chars of the string to
     * reduce the string to a maximum of MAX_SIZE characters
     *
     * @param ch the char to append to the end
     */
    public void add(String ch) {
        if (ch == null) {
            return;
        }

        if (ch.equals("Backspace")) {
            buffer = buffer.substring(0, buffer.length()-1);
        } else {
            buffer = buffer + ch;
        }

        if (buffer.length() > MAX_SIZE) {
            buffer = buffer.substring(1);
        }

        System.out.println("BUFFER"+ buffer);
    }

    public boolean contains(List<String> strings) {
        return strings.stream()
                .anyMatch(s -> buffer.contains(s.toUpperCase()));
    }

     public void clear (){
         buffer = "";
     }
}

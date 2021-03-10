package fr.raluy.chocoratage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static java.lang.String.join;
import static java.util.Collections.reverse;

public class KeyBuffer implements CharacterSource {
    private static final int MAX_WORDS = 10;
    private static final int MAX_WORD_SIZE = 100;

    private final LinkedList<String> previousWords = new LinkedList<>();
    private final StringBuilder currentWord = new StringBuilder(MAX_WORD_SIZE);


    public void submitString(String str) {
        for (char c : str.toCharArray()) {
            submitChar(c);
        }
    }

    /**
     * Adds a char at the end of the string, and evicts first chars of the string to
     * reduce the string to a maximum of MAX_SIZE characters
     *
     * The method accepts backspace and all sorts
     * Read this too https://docs.oracle.com/javase/tutorial/java/data/characters.html
     *
     * @param keyChar the char to append to the end
     * @return true if the buffer has changed
     */
    public synchronized boolean submitChar(char keyChar) {
        if (appendLetterOrDigit(keyChar)) {
            return true;
        }
        else if (isBackspace(keyChar)) {
            return backspace();
        }
        // TODO how to handle DEL? Maybe only after arrows have been pressed? Not from here anyway as arrows are not chars.
        else if (isControlCharOrSpace(keyChar)) { // space, tab, newline...let's start a new word
            newWord(); // currentWord contents is moving to the previousWords but the buffer remains the same => return false
        }
        // else might be &"'(-_)=~#{[|`\^@]}£$¤+-*/=!?,;.:/§<> and others => ignoring
        return false;
        // TODO handle paste the best we can
    }

    /**
     * Appends a letter or digit character to the buffer
     * @param keyChar
     * @return true if the buffer changed or false as the character was neither a letter nor a digit and couldn't be appended
     */
    public boolean appendLetterOrDigit(char keyChar) {
        boolean result = isLetterOrDigit(keyChar);

        if (result) {
            currentWord.append(Character.toLowerCase(keyChar));

            int currentSize = currentWord.length();
            if (currentSize > MAX_WORD_SIZE) {
                currentWord.delete(0, currentSize - MAX_WORD_SIZE); //cut the buffer back to MAX_SIZE chars
            }
        }
        return result;
    }

    /**
     * Removes the trailing char of the buffer.
     * of course this way of working won't work correctly if someone types in 2 separators then backspaces twice => one character too many is removed
     * @return true if the buffer changed
     */
    public boolean backspace() {
        if (isCurrentWordPresent()) {
            int currentSize = currentWord.length();
            currentWord.delete(currentSize - 1, currentSize);
        }
        else if(!previousWords.isEmpty()) { // here currentWord has already a zero-length
            String previousWord = previousWords.removeLast();
            currentWord.append(previousWord, 0, previousWord.length()-1);
        } else {
            return false;
        }
        return true;
    }

    public void newWord() {
        if (isCurrentWordPresent()) {
            if (previousWords.size() == MAX_WORDS) {
                previousWords.removeFirst();
            }
            previousWords.add(currentWord.toString());
            currentWord.setLength(0);
        }
    }

    /**
     * @param keyChar
     * @return
     */
    public static boolean isLetterOrDigit(char keyChar) {
        return Character.isLetterOrDigit(keyChar);
    }

    public static boolean isBackspace(char keyChar) {
        return keyChar == KeyEvent.VK_BACK_SPACE; // NOT NativeKeyEvent.VC_BACKSLASH, because we are dealing with a key char here (\b == 8), NOT a key code (0x000E)
    }

    /**
     * http://www.physics.udel.edu/~watson/scen103/ascii.html
     *
     * @param keyChar
     * @return true if the input char is in the range [0x00-0x1F] or DEL (0x7F) or space (0x20)
     */
    public static boolean isControlCharOrSpace(char keyChar) {
        return keyChar <= ' ' || isDelete(keyChar);
    }

    public static boolean isDelete(char keyChar) {
        return keyChar == KeyEvent.VK_DELETE; // NOT NativeKeyEvent.VC_DELETE, because we are dealing with a key char here (0x7F), NOT a key code (0x0E53)
    }


    @Override
    public String getCurrentWord() {
        return currentWord.toString();
    }

    private boolean isCurrentWordPresent() {
        return currentWord.length() > 0;
    }

    @Override
    public int getWordCount() {
        return previousWords.size() + (isCurrentWordPresent() ? 1 : 0);
    }

    public List<String> getLatestWords(int neededWords) {
        int wordCount = getWordCount();
        if (neededWords > wordCount) {
            throw new IllegalArgumentException("available words: " + wordCount + ", requested: " + neededWords);
        }

        List<String> result = new ArrayList<>(neededWords);
        if (neededWords > 0) {
            int previousWordsSize = previousWords.size();
            if (isCurrentWordPresent()) {
                result.addAll(previousWords.subList(previousWordsSize - neededWords + 1, previousWordsSize));
                result.add(currentWord.toString());
            } else {
                result.addAll(previousWords.subList(previousWordsSize - neededWords, previousWordsSize));
            }
        }
        return result;
    }

    @Override
    public int getCharCount() {
        return previousWords.stream().mapToInt(word -> word.length()).sum() + currentWord.length();
    }

    @Override
    public List<String> getLatestChars(int neededChars) {
        int charCount = getCharCount();
        if(neededChars > charCount) {
            throw new IllegalArgumentException("available chars: " + charCount + ", requested: " + neededChars);
        }

        List<String> result = new ArrayList<>();

        if (isCurrentWordPresent()) {
            String currentWord = getCurrentWord();
            result.add(currentWord);
            neededChars -= currentWord.length();
        }

        ListIterator<String> pwit = previousWords.listIterator(previousWords.size());
        while (neededChars > 0) { // no need to check the iterator since we know neededChars <= charCount
            String previousWord = pwit.previous();
            neededChars -= previousWord.length();
            if(neededChars < 0) {
                previousWord = previousWord.substring(0, previousWord.length() + neededChars);
            }
            result.add(previousWord);
        }
        reverse(result);
        return result;
    }

    public void clear() {
        previousWords.clear();
        currentWord.setLength(0);
    }

    @Override
    public String toString() {
        return join(" ", getLatestWords(getWordCount()));
    }
}

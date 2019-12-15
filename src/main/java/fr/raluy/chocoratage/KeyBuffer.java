package fr.raluy.chocoratage;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class KeyBuffer {
    public static final int MAX_WORDS = 10;
    private final LinkedList<String> previousWords = new LinkedList<>();

    private int MAX_WORD_SIZE = 100;
    private final StringBuilder currentWord = new StringBuilder(MAX_WORD_SIZE);

    private final Predicate<ForbiddenPhrase> matchingPredicate;


    private final Predicate<ForbiddenPhrase> levenshteinPredicate = (fp) -> {
        String challenge = getLatestAsString(fp.getWordCount());
        int challengeLength = challenge.length();
        if(challengeLength <= 4 && challengeLength < fp.getPhraseLength()) { // to prevent borderline positives like "choc" for "choco" => will work for "choko" though
            return false;
        } else {
            return Levenshtein.isThisSimilarEnoughToThat(fp.getPhraseLower(), challenge, false);
        }
    };
    private final Predicate<ForbiddenPhrase> equalsPredicate = (fp) -> fp.getPhraseLower().equals(getLatestAsString(fp.getWordCount()));

    public KeyBuffer(boolean relax) {
        matchingPredicate = relax ? levenshteinPredicate : equalsPredicate;
    }

    /**
     * Adds a char at the end of the string, and evicts first chars of the string to
     * reduce the string to a maximum of MAX_SIZE characters
     *
     * @param keyEvent the char to append to the end
     */
    public synchronized void add(NativeKeyEvent keyEvent) {
        if (keyEvent == null) {
            return;
        }

        if (isLetterOrDigit(keyEvent)) {
            String keyText = keyEvent.getKeyText(keyEvent.getKeyCode());
            currentWord.append(keyText.toLowerCase());

            if (currentWord.length() > MAX_WORD_SIZE) {
                currentWord.delete(0, currentWord.length() - MAX_WORD_SIZE); //cut the buffer back to MAX_SIZE chars
            }
        }
        else if(isCurrentWordExists()) { // let's start a new word
            if(previousWords.size() == MAX_WORDS) {
                previousWords.removeFirst();
            }
            previousWords.add(currentWord.toString());
            currentWord.setLength(0);
        }
        // TODO handle backspace, paste the best we can


    }

    private static boolean isLetterOrDigit(NativeKeyEvent keyEvent) {
        if (keyEvent.isActionKey()) {
            return false;
        }

        String keyText = NativeKeyEvent.getKeyText(keyEvent.getKeyCode());

        if (keyText.length() > 1) {
            return false;
        } else return Character.isLetterOrDigit(keyText.codePointAt(0));
    }


    public boolean containsIgnoreCase(Collection<ForbiddenPhrase> phrases) {
        int wordCount = getWordCount();
        return phrases.stream().filter(p -> p.getWordCount() <= wordCount).anyMatch(matchingPredicate);
    }

    private boolean isCurrentWordExists() {
        return currentWord.length() > 0;
    }

    private String getLatestAsString(int c) {
        return String.join(" ", getLatest(c));
    }

    private List<String> getLatest(int c) {
        int wordCount = getWordCount();
        if (c > wordCount) {
            throw new IllegalArgumentException("available: " + wordCount + ", requested: " + c);
        }
        List<String> result = new ArrayList<>(c);
        if(c > 0) {
            int previousWordsSize = previousWords.size();
            if (isCurrentWordExists()) {
                result.addAll(previousWords.subList(previousWordsSize - c + 1 , previousWordsSize));
                result.add(currentWord.toString());
            } else {
                result.addAll(previousWords.subList(previousWordsSize - c, previousWordsSize));
            }
        }
        return result;
    }

    private int getWordCount() {
        return previousWords.size() + (isCurrentWordExists() ? 1 : 0);
    }

    public void clear() {
        previousWords.clear();
        currentWord.setLength(0);
    }

    @Override
    public String toString() {
        return getLatestAsString(getWordCount());
    }
}

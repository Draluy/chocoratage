package fr.raluy.chocoratage;

import java.util.List;

public interface CharacterSource {

    int getWordCount();
    List<String> getLatestWords(int wordCount);
    String getCurrentWord();

    int getCharCount();
    List<String> getLatestChars(int charCount);
}

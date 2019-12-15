package fr.raluy.chocoratage;

import java.util.regex.Pattern;

public class ForbiddenPhrase {
  public static final Pattern WORD_SEPARATOR = Pattern.compile("[ \\t]+");

  private String phraseLower;
  private int phraseLength;
  private int wordCount;

  /**
   * Constructs a ForbiddenPhrase instance
   * Trims and converts the input to lowercase along the way
   * @param phrase
   * TODO handle accents (Normalizer?)
   */
  public ForbiddenPhrase(String phrase) {
    String cleanPhrase = WORD_SEPARATOR.matcher(phrase.trim()).replaceAll(" ");
    this.phraseLower = cleanPhrase.toLowerCase();
    this.phraseLength = this.phraseLower.length();

    String[] wordsLower = WORD_SEPARATOR.split(this.phraseLower); // if you want to make them available to the outside, please use a unmodifiableList
    this.wordCount = wordsLower.length;
  }

  public String getPhraseLower() {
    return phraseLower;
  }

  public int getPhraseLength() {
    return phraseLength;
  }

  public int getWordCount() {
    return wordCount;
  }
}

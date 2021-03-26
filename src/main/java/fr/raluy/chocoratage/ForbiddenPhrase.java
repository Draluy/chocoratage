package fr.raluy.chocoratage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static fr.raluy.chocoratage.Normalizer.normalize;

public class ForbiddenPhrase {

  private String phraseLower;
  private int phraseLength;
  private String token;
  private final int tokenLength;
  private int wordCount;

  public ForbiddenPhrase(String phrase) {
    this(phrase, null);
  }

    /**
     * Constructs a ForbiddenPhrase instance
     * Trims and converts the input to lowercase along the way
     * @param phrase
     * @param locale
     */
  public ForbiddenPhrase(String phrase, Locale locale) {
    phrase = phrase.toLowerCase();
    List<String> wordsLower = spaceSplit(phrase); // if you want to make them available to the outside, please use a unmodifiableList
    this.wordCount = wordsLower.size();
    this.phraseLower = String.join(" ", wordsLower);
    this.phraseLength = this.phraseLower.length();
    this.token = normalize(locale, this.phraseLower);
    this.tokenLength = this.token.length();
  }

  /**
   * Splits a string at each space encountered (eg: "foo bar" => {"foo", "bar"}, "foo  bar" => { "foo", "", "bar" })
   * Obviously faster than a regexp
   * @param str
   * @return the split string
   */
  public static List<String> spaceSplit(String str) {
    char[] chars = str.toCharArray();
    int i = 0, l = chars.length;
    StringBuilder sb = new StringBuilder();
    List<String> parts = new ArrayList();

    do {
      while(i < l && isSpace(chars[i])) {
        i++;
      }

      char c;
      while(i < l && !isSpace(c = chars[i])) {
        sb.append(c);
        i++;
      }

      if(sb.length() > 0) {
        parts.add(sb.toString());
        sb.setLength(0);
      }
    } while(i+1 < l);
    return parts;
  }

  private static boolean isSpace(char c) {
    return c == ' ' || c == '\t';
  }

  public String getPhraseLower() {
    return phraseLower;
  }

  public int getPhraseLength() {
    return phraseLength;
  }

  public String getToken() {
    return token;
  }

  public int getTokenLength() {
    return tokenLength;
  }

  public int getWordCount() {
    return wordCount;
  }
}

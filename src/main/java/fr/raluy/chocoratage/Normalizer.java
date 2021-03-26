package fr.raluy.chocoratage;

import com.ibm.icu.text.Transliterator;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;

public class Normalizer {

  private static final Transliterator transliterator;

  private final static Pattern pluralPattern = Pattern.compile("s(\\b|$)", Pattern.CASE_INSENSITIVE);

  private final static Pattern PATTERN_PH = Pattern.compile("ph", Pattern.CASE_INSENSITIVE);
  public static final String REPLACEMENT_PH = "f";
  private final static Pattern PATTERN_CH = Pattern.compile("ch", Pattern.CASE_INSENSITIVE);
  public static final String REPLACEMENT_CH = "sh";
  private final static Pattern PATTERN_TH = Pattern.compile("th", Pattern.CASE_INSENSITIVE);
  public static final String REPLACEMENT_TH = "t";


  static {
    // Converts to latin, strips (nearly) all, and converts to lowercase (same case as KeyBuffer's storage)
    // Add [:Symbol:] between [:Punctuation:] and [:Modifier Letter:] to remove emojis (and pretty much all the rest of the punctuation)
    transliterator = Transliterator.getInstance("Latin; NFD; [[:Space:][:Control:][:Punctuation:][:Modifier Letter:][:Other Number:][:Nonspacing Mark:]] Remove; NFC; Lower;");
  }

  public static String normalize(String... words) {
    return normalize(null, words);
  }

  public static String normalize(Locale locale, String... words) {
    return normalize(locale, asList(words));
  }

  public static String normalize(List<String> words) {
    return normalize(null, words);
  }

    public static String normalize(Locale locale, List<String> words) {
    StringBuffer sb = new StringBuffer();

    for (String word : words) {
      if (word != null) {
        String stripped = stripPlural(word);

        if (locale != null) {
          stripped = stripLinkingWords(locale, stripped);
        }
        sb.append(stripped);
      }
    }

    String stripped = sb.toString();
    String transliterated = removeNonEmojiSymbols(transliterator.transliterate(stripped));
    String tokenized = stripDuplicateChars(stripPhuture(transliterated));
    return tokenized;
  }

  /**
   * Removes works like "The" (EN) or "Le/La) (FR) to improve matching
   * @param locale
   * @param str
   * @return
   */
  private final static String stripLinkingWords(Locale locale, String str) {
    if(str != null) {
      // Not necessary for now
    }
    return str;
  }

  private final static String stripPlural(String str) {
    if (str != null) {
      str = pluralPattern.matcher(str).replaceAll("$1");
    }
    return str;
  }

  /**
   * https://en.wikipedia.org/wiki/Basic_Latin_(Unicode_block)#Number_of_symbols,_letters_and_control_codes
   * https://en.wikipedia.org/wiki/Latin-1_Supplement_(Unicode_block)#Character_table
   * @param str
   * @return
   */
  private static String removeNonEmojiSymbols(String str) {
    StringBuilder sb = new StringBuilder(str.length());
    str.codePoints().sequential()
            .filter(value ->
                  value == 0x20 // space
              || (value >= 0x30 && value <= 0x39) // [0-9]
              || (value >= 0x41 && value <= 0x5A) // [A-Z]
              || (value >= 0x61 && value <= 0x7A) // [a-z]
              || (value >= 0xC0 && value != 0xD7 && value != 0xF7)) // Latin supplement excluding C1 controls
            .mapToObj(Character::toChars)                           // punctuation & symbols, ×, ÷, and everything from 0x100
            .forEach(chars -> sb.append(chars)); // faster than reduce



    return sb.toString();
  }

  /**
   * Transforms ph => f, ch -> sh, th -> t
   * A little too latin-colored
   * @param str
   * @return
   */
  private final static String stripPhuture(String str) {
    if (str != null) {
      str = PATTERN_PH.matcher(str).replaceAll(REPLACEMENT_PH);
      str = PATTERN_CH.matcher(str).replaceAll(REPLACEMENT_CH);
      str = PATTERN_TH.matcher(str).replaceAll(REPLACEMENT_TH);
    }
    return str;
  }

  private final static String stripDuplicateChars(String str) {
    if (str != null && !str.isEmpty()) {
      char old = (str.charAt(0) == Character.MIN_VALUE) ? Character.MAX_VALUE : Character.MIN_VALUE;

      StringBuffer sb = new StringBuffer();
      char[] testArray = str.toUpperCase().toCharArray();
      char[] strArray = str.toCharArray();

      for (int i = 0; i < testArray.length; i++) {
        char c = testArray[i];
        if (c != old || isDigit(c)) {
          sb.append(strArray[i]);
          old = c;
        }
      }
      str = sb.toString();
    }
    return str;
  }
}

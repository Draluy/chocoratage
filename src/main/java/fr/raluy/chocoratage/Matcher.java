package fr.raluy.chocoratage;

import java.util.Collection;

import static fr.raluy.chocoratage.Normalizer.normalize;

public enum Matcher {
  EQUAL {

    public boolean matches(CharacterSource source, ForbiddenPhrase fp) {
      int fpWordCount = fp.getWordCount();
      return fpWordCount <= source.getWordCount() && fp.getPhraseLower().equals(getLatestWordsAsString(source, fpWordCount));
    }
  },


  LEVENSHTEIN {

    public boolean matches(CharacterSource source, ForbiddenPhrase fp) {
      final int fpWordCount = fp.getWordCount();
      if(fpWordCount <= source.getWordCount()) {
        String challenge = getLatestWordsAsString(source, fpWordCount);
        int challengeLength = challenge.length();
        if (challengeLength > MIN_CHALLENGE_LENGTH || challengeLength >= fp.getPhraseLength()) { // to prevent borderline positives like "choc" for "choco" => will work for "choko" though
          return Levenshtein.isThisSimilarEnoughToThat(fp.getPhraseLower(), challenge, false);
        }
      }
      return false;
    }
  },


  NORMALIZER {

    public boolean matches(CharacterSource source, ForbiddenPhrase fp) {
      if (fp.getTokenLength() <= source.getCharCount()) {
        String latestCharsNormalized = normalize(source.getLatestChars(fp.getTokenLength()));
        return Levenshtein.isThisSimilarEnoughToThat(fp.getToken(), latestCharsNormalized, NORMALIZER_MAX_DISTANCE, false);
      } else {
        return false;
      }
    }
  };


  public static final int MIN_CHALLENGE_LENGTH = 4;
  public static final int NORMALIZER_MAX_DISTANCE = 2;


  public boolean matches(CharacterSource source, Collection<ForbiddenPhrase> fps) {
    return fps.stream().anyMatch(fp -> matches(source, fp));
  }

  public abstract boolean matches(CharacterSource source, ForbiddenPhrase fp);

  private static String getLatestWordsAsString(CharacterSource source, int wordCount) {
    return String.join(" ", source.getLatestWords(wordCount));
  }
}

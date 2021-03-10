package fr.raluy.chocoratage;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static fr.raluy.chocoratage.ForbiddenPhrase.spaceSplit;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForbiddenPhraseTest {

  @Test
  public void testWord() {
    assertEquals(Arrays.asList("foobar"), spaceSplit("foobar"));
  }

  @Test
  public void testSentence() {
    assertEquals(Arrays.asList("fOo", "BaR", "baZ"), spaceSplit("fOo  BaR\tbaZ"));
  }

  @Test
  public void testLeading() {
    assertEquals(Arrays.asList("fOo", "BaR"), spaceSplit("        fOo  BaR"));
  }

  @Test
  public void testTrailing() {
    assertEquals(Arrays.asList("BaR", "baZ"), spaceSplit("BaR\tbaZ\t\t"));
  }

  @Test
  public void testBlank() {
    assertEquals(emptyList(), spaceSplit(" \t \t \t"));
  }

  @Test
  public void testEmpty() {
    assertEquals(emptyList(), spaceSplit(""));
  }

  @Test
  public void testNull() {
    assertThrows(NullPointerException.class, () -> spaceSplit(null));
  }
}

package fr.raluy.chocoratage;


import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.stream.IntStream;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This file MUST be encoded in unicode due to the use of non-latin chars
 */
public class LevenshteinBufferTest {
    public static final String ONE_HUNDRED_CHARS_UPPER = "THISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234";
    public static final String ONE_HUNDRED_CHARS_LOWER = ONE_HUNDRED_CHARS_UPPER.toLowerCase();

    KeyBuffer keysBuffer = new KeyBuffer();
    Matcher matcher = Matcher.LEVENSHTEIN;

    @Test
    public void shouldBeEmptyAtFirst() {
        assertThat(keysBuffer.toString()).isEqualTo("");
    }

    @Test
    public void shouldAddStringToEmptyBuffer() {
        keysBuffer.submitString("TEST");

        assertThat(keysBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAddStringsToBuffers() {
        keysBuffer.submitString("TEST ");
        keysBuffer.submitString("HAS\t\n");
        keysBuffer.submitString("FOUR\f ");
        keysBuffer.submitString("WORDS");

        assertThat(keysBuffer.toString()).isEqualTo("test has four words");
    }

    @Test
    public void shouldAddAccentedCharacters() {
        keysBuffer.submitString("TEST ÉùàçØöğ");
        assertThat(keysBuffer.toString()).isEqualTo("test éùàçøöğ");
    }

    @Test
    public void shouldAddNonLatinStringToBuffer() {
        keysBuffer.submitString("La"); // Latin
        keysBuffer.submitString("αΨ"); // Greek
        keysBuffer.submitString("Жц"); // Cyrillic
        keysBuffer.submitString("թխ"); // Armenian
        keysBuffer.submitString("جؼ"); // Arabic
        keysBuffer.submitString("שמ"); // Hebrew
        keysBuffer.submitString("葵袷"); // Kanji
        keysBuffer.submitString("ばふ"); // Hiragana
        keysBuffer.submitString("하함"); // Korean
        keysBuffer.submitString("腌盒"); // Chinese
        keysBuffer.submitString("༺༪"); // Tibetan
        keysBuffer.submitString("บพ"); // Thai
        keysBuffer.submitString("ങജ"); // Malayalam
        keysBuffer.submitString("ছয়"); // Bengali
        keysBuffer.submitString("ఞఌ"); // Telugu
        assertThat(keysBuffer.toString()).isEqualTo("laαψжцթխجؼשמ葵袷ばふ하함腌盒บพങജছয়ఞఌ");
    }

    @Test
    public void shouldNotAddNonLetterOrDigit() {
        keysBuffer.submitString("♥♥♥");
        keysBuffer.submitString("LOVE");
        keysBuffer.submitString("♥♥♥");
        keysBuffer.submitString("&\"'(-_)=~#{[|`\\^@]}£$¤+-*/=!?,;.:/§<>");
        assertThat(keysBuffer.toString()).isEqualTo("love");
    }

    @Test
    public void shouldAllowBackspace() {
        keysBuffer.submitString("TEST0");
        keysBuffer.submitChar('\b');

        assertThat(keysBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAllow100Chars() {
        keysBuffer.submitString(ONE_HUNDRED_CHARS_UPPER + " ");

        assertThat(keysBuffer.toString()).hasSize(100);
        assertThat(keysBuffer.toString()).isEqualTo(ONE_HUNDRED_CHARS_LOWER);
    }

    @Test
    public void shouldReplaceEndingCharsWhenFull() {
        keysBuffer.submitString(ONE_HUNDRED_CHARS_UPPER);
        keysBuffer.submitString("REPLACEMENT");

        assertThat(keysBuffer.toString()).hasSize(100);
        assertThat(keysBuffer.toString()).isEqualTo("nehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234replacement");
    }


    @Test
    public void testContains() {
        keysBuffer.submitString("HUNDRED ");
        boolean result = matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("hundred")));
        assertTrue(result);

        keysBuffer.submitString("1234");
        result = matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("1234")));
        assertTrue(result);

        result = matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("hundred 1234")));
        assertTrue(result);
    }

    @Test
    public void testDoesNotContain() {
        keysBuffer.submitString(ONE_HUNDRED_CHARS_LOWER);

        boolean result = matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("eleven")));
        assertFalse(result);
    }

    @Test
    public void testAddingSeveralSpacesShouldNotReplaceWords() {
        keysBuffer.submitString("REPLACEMENT ");
        keysBuffer.submitString(" ");
        keysBuffer.submitString(";");
        keysBuffer.submitString(" ");
        keysBuffer.submitString(":");
        keysBuffer.submitString("ADD");

        assertThat(keysBuffer.toString()).isEqualTo("replacement add");
    }

    @Test
    public void testClear() {
        keysBuffer.submitString("replacement");

        keysBuffer.clear();

        assertThat(keysBuffer.toString()).isEmpty();
    }

    @Test
    public void shouldMatchApproiximateWoords() {
        keysBuffer.submitString("choKo ");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("choco"))));

        keysBuffer.submitString("croissanst");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("croissants"))));
        assertFalse(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("decroissants"))));
    }


    private void addNChars(int n) {
        IntStream.range(0, n)
                .forEach(i -> keysBuffer.submitChar('A'));
    }

    private NativeKeyEvent createNativeKeyEvent(int keyCode) {
        return new NativeKeyEvent(
                NativeKeyEvent.NATIVE_KEY_PRESSED,
                0x00,        // Modifiers
                0x00,        // Raw Code
                keyCode,
                NativeKeyEvent.CHAR_UNDEFINED,
                NativeKeyEvent.KEY_LOCATION_STANDARD);
    }


    private NativeKeyEvent createNativeKeyEvent(char c) {
        try {
            Field field = getFieldFromChar(c);
            return new NativeKeyEvent(
                    NativeKeyEvent.NATIVE_KEY_PRESSED,
                    0x00,        // Modifiers
                    0x00,        // Raw Code
                    field.getInt(null),
                    NativeKeyEvent.CHAR_UNDEFINED,
                    NativeKeyEvent.KEY_LOCATION_STANDARD);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Field getFieldFromChar(char c) throws NoSuchFieldException {
        char uc = Character.toUpperCase(c);
        String strToAdd = uc + "";
        if (uc == ' ') {
            strToAdd = "SPACE";
        }
        if (uc == ';') {
            strToAdd = "SEMICOLON";
        }
        if (uc == ':') {
            strToAdd = "COMMA";
        }

        return NativeKeyEvent.class.getField("VC_" + strToAdd);

    }
}

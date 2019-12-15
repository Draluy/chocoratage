package fr.raluy.chocoratage;


import org.jnativehook.keyboard.NativeKeyEvent;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.stream.IntStream;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class KeysBufferTest {
    public static final String ONE_HUNDRED_CHARS_UPPER = "THISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234";
    public static final String ONE_HUNDRED_CHARS_LOWER = ONE_HUNDRED_CHARS_UPPER.toLowerCase();
    KeyBuffer keysBuffer = new KeyBuffer(true);

    @Test
    public void shouldBeEmptyAtFirst() {
        assertThat(keysBuffer.toString()).isEqualTo("");
    }

    @Test
    public void shouldAddStringToEmptyBuffer() {
        addStringToBuffer("TEST");

        assertThat(keysBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAddStringToBuffer() {
        addStringToBuffer("TEST ");
        addStringToBuffer("HAVE");

        assertThat(keysBuffer.toString()).isEqualTo("test have");
    }

    @Test
    public void shouldNotAddNull() {
        addStringToBuffer("TEST");
        keysBuffer.add(null);

        assertThat(keysBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAllow100Chars() {
        addStringToBuffer(ONE_HUNDRED_CHARS_UPPER + " ");

        assertThat(keysBuffer.toString()).hasSize(100);
        assertThat(keysBuffer.toString()).isEqualTo(ONE_HUNDRED_CHARS_LOWER);
    }

    @Test
    public void shouldReplaceEndingCharsWhenFull() {
        addStringToBuffer(ONE_HUNDRED_CHARS_UPPER);
        addStringToBuffer("REPLACEMENT");

        assertThat(keysBuffer.toString()).hasSize(100);
        assertThat(keysBuffer.toString()).isEqualTo("nehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234replacement");
    }


    @Test
    public void testContains() {
        addStringToBuffer("HUNDRED ");
        boolean result = keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("hundred")));
        assertThat(result).isTrue();

        addStringToBuffer("1234");
        result = keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("1234")));
        assertThat(result).isTrue();

        result = keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("hundred 1234")));
        assertThat(result).isTrue();
    }

    @Test
    public void testDoesNotContain() {
        addStringToBuffer(ONE_HUNDRED_CHARS_LOWER);

        boolean result = keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("eleven")));
        assertThat(result).isFalse();
    }

    @Test
    public void testAddingSeveralSpacesShouldNotReplaceWords() {
        addStringToBuffer("REPLACEMENT ");
        addStringToBuffer(" ");
        addStringToBuffer(";");
        addStringToBuffer(" ");
        addStringToBuffer(":");
        addStringToBuffer("ADD");

        assertThat(keysBuffer.toString()).isEqualTo("replacement add");
    }

    @Test
    public void testClear() {
        addStringToBuffer("replacement");

        keysBuffer.clear();

        assertThat(keysBuffer.toString()).isEmpty();
    }

    @Test
    public void shouldMatchApproiximateWoords() {
        addStringToBuffer("choKo ");
        assertThat(keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("choco")))).isTrue();

        addStringToBuffer("croissanst");
        assertThat(keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("croissants")))).isTrue();
        assertThat(keysBuffer.containsIgnoreCase(singleton(new ForbiddenPhrase("decroissants")))).isFalse();
    }


    private void addStringToBuffer(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i); //magic offset
            keysBuffer.add(createNativeKeyEvent(c));
        }
    }

    private void addNChars(int n) {
        IntStream.range(0, n)
                .forEach(i -> keysBuffer.add(createNativeKeyEvent(NativeKeyEvent.VC_A)));
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

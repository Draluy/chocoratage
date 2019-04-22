package fr.raluy.chocoratage;


import org.assertj.core.api.Assertions;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CircularBufferTest {
    CircularBuffer circularBuffer = new CircularBuffer();

    @Test
    public void shouldBeEmptyAtFirst() throws Exception {
        assertThat(circularBuffer.toString()).isEqualTo("");
    }

    @Test
    public void shouldAddStringToEmptyBuffer() throws Exception {
        addStringToBuffer("test");

        assertThat(circularBuffer.toString()).isEqualTo("TEST");
    }

    @Test
    public void shouldAddStringToBuffer() throws Exception {
        addStringToBuffer("test ");
        addStringToBuffer("have");

        assertThat(circularBuffer.toString()).isEqualTo("TEST HAVE");
    }

    @Test
    public void shouldNotAddNull() throws Exception {
        addStringToBuffer("test");
        circularBuffer.add(null);

        assertThat(circularBuffer.toString()).isEqualTo("TEST");
    }

    @Test
    public void shouldAllow100Chars() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234 ");

        assertThat(circularBuffer.toString()).hasSize(100);
        assertThat(circularBuffer.toString()).isEqualTo("THISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234");
    }

    @Test
    public void shouldReplaceEndingCharsWhenFull() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");
        addStringToBuffer("replacement");

        assertThat(circularBuffer.toString()).hasSize(100);
        assertThat(circularBuffer.toString()).isEqualTo("NEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234REPLACEMENT");
    }


    @Test
    public void testContains() throws Exception {
        addStringToBuffer("HUNDRED ");
        addStringToBuffer("1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("hundred"));
        assertThat(result).isTrue();

        result = circularBuffer.containsUppercase(Arrays.asList("1234"));
        assertThat(result).isTrue();
    }

    @Test
    public void testDoesNotContain() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("eleven"));
        assertThat(result).isFalse();
    }

    @Test
    public void testAddingSeveralSpacesShouldNotReplaceWords() throws Exception {
        addStringToBuffer("replacement ");
        addStringToBuffer(" ");
        addStringToBuffer(";");
        addStringToBuffer(" ");
        addStringToBuffer(":");
        addStringToBuffer("add");

        assertThat(circularBuffer.toString()).isEqualTo("REPLACEMENT ADD");
    }

    @Test
    public void wordsShouldReplaceEachOther() throws Exception {
        addStringToBuffer("replacement ");
        addStringToBuffer("bunny ");
        addStringToBuffer("road ");
        addStringToBuffer("add");

        assertThat(circularBuffer.toString()).isEqualTo("ROAD ADD");
    }

    @Test
    public void testClear() throws Exception {
        addStringToBuffer("replacement");

        circularBuffer.clear();

        assertThat(circularBuffer.toString()).isEmpty();
    }

    @Test
    public void shouldMatchApproiximateWoords() {
        addStringToBuffer("choKo ");
        addStringToBuffer("croissanst");

        assertThat(circularBuffer.containsUppercase(Arrays.asList("choco"))).isTrue();
        assertThat(circularBuffer.containsUppercase(Arrays.asList("croissants"))).isTrue();
        assertThat(circularBuffer.containsUppercase(Arrays.asList("decroissants"))).isFalse();
    }


    private void addStringToBuffer(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i); //magic offset
            circularBuffer.add(createNativeKeyEvent(c));
        }
    }

    private void addNChars(int n) {
        IntStream.range(0, n)
                .forEach(i -> circularBuffer.add(createNativeKeyEvent(NativeKeyEvent.VC_A)));
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

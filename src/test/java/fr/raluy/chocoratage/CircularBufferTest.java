package fr.raluy.chocoratage;


import org.assertj.core.api.Assertions;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CircularBufferTest {
    CircularBuffer circularBuffer = new CircularBuffer();

    @Test
    public void shouldBeEMptyAtFirst() throws Exception {
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("");
    }

    @Test
    public void shouldAddStringToEmptyBuffer() throws Exception {
        addStringToBuffer("test");

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("TEST");
    }

    @Test
    public void shouldAddStringToBuffer() throws Exception {
        addStringToBuffer("test");
        addStringToBuffer("have");

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("TESTHAVE");
    }

    @Test
    public void shouldNotAddNull() throws Exception {
        addStringToBuffer("test");
        circularBuffer.add(null);

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("TEST");
    }

    @Test
    public void shouldAllow100Chars() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        Assertions.assertThat(circularBuffer.toString()).hasSize(100);
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("THISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234");
    }

    @Test
    public void shouldReplaceEndingCharsWhenFull() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");
        addStringToBuffer("replacement");

        Assertions.assertThat(circularBuffer.toString()).hasSize(100);
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("NEHUNDREDCHARACTERSLONGBELIEVEITORNOTTHISLINEISONEHUNDREDCHARACTERSLONGBELIEVEITORNOT1234REPLACEMENT");
    }


    @Test
    public void testContains() throws Exception {
        addStringToBuffer("THISLINEisoneHUNDREDcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("hundred"));
        Assertions.assertThat(result).isTrue();

        result = circularBuffer.containsUppercase(Arrays.asList("1234"));
        Assertions.assertThat(result).isTrue();

        result = circularBuffer.containsUppercase(Arrays.asList("thisline"));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDoesNotContain() throws Exception {
        addStringToBuffer("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("eleven"));
        Assertions.assertThat(result).isFalse();
    }


    @Test
    public void testClear() throws Exception {
        addStringToBuffer("replacement");

        circularBuffer.clear();

        Assertions.assertThat(circularBuffer.toString()).isEmpty();
    }

    private void addStringToBuffer (String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i); //magic offset
            circularBuffer.add(createNativeKeyEvent(c));
        }
    }

    private void addNChars (int n){
        IntStream.range(0,n)
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


    private NativeKeyEvent createNativeKeyEvent(char c)  {
        try {
            Field field = NativeKeyEvent.class.getField("VC_" + Character.toUpperCase(c));
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


}

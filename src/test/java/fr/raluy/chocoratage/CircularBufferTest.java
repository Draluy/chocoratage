package fr.raluy.chocoratage;


import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

public class CircularBufferTest {
    CircularBuffer circularBuffer = new CircularBuffer();

    @Test
    public void shouldBeEMptyAtFirst() throws Exception {
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("");
    }

    @Test
    public void shouldAddStringToEmptyBuffer() throws Exception {
        circularBuffer.add("test");

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAddStringToBuffer() throws Exception {
        circularBuffer.add("test");
        circularBuffer.add("have");

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("testhave");
    }

    @Test
    public void shouldNotAddNull() throws Exception {
        circularBuffer.add("test");
        circularBuffer.add(null);

        Assertions.assertThat(circularBuffer.toString()).isEqualTo("test");
    }

    @Test
    public void shouldAllow100Chars() throws Exception {
        circularBuffer.add("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        Assertions.assertThat(circularBuffer.toString()).hasSize(100);
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");
    }

    @Test
    public void shouldReplaceEndingCharsWhenFull() throws Exception {
        circularBuffer.add("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");
        circularBuffer.add("replacement");

        Assertions.assertThat(circularBuffer.toString()).hasSize(100);
        Assertions.assertThat(circularBuffer.toString()).isEqualTo("nehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234replacement");
    }


    @Test
    public void testContains() throws Exception {
        circularBuffer.add("THISLINEisoneHUNDREDcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("hundred"));
        Assertions.assertThat(result).isTrue();

        result = circularBuffer.containsUppercase(Arrays.asList("1234"));
        Assertions.assertThat(result).isTrue();

        result = circularBuffer.containsUppercase(Arrays.asList("thisline"));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDoesNotContain() throws Exception {
        circularBuffer.add("thislineisonehundredcharacterslongbelieveitornotthislineisonehundredcharacterslongbelieveitornot1234");

        boolean result = circularBuffer.containsUppercase(Arrays.asList("eleven"));
        Assertions.assertThat(result).isFalse();
    }


    @Test
    public void testClear() throws Exception {
        circularBuffer.add("replacement");

        circularBuffer.clear();

        Assertions.assertThat(circularBuffer.toString()).isEmpty();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
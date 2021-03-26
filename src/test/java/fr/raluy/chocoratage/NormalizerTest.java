package fr.raluy.chocoratage;


import org.junit.jupiter.api.Test;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This file MUST be encoded in unicode due to the use of non-latin chars
 */
public class NormalizerTest {

    @Test
    public void shouldNormalizeLatinCorrectly() {
        String latin = "demain j'amène les chocos pour tout le monde :)";
        String token = Normalizer.normalize(latin);
        assertThat(token).isEqualTo("demainjameneleshocopourtoutlemonde");
    }

    @Test
    public void shouldNormalizeAccentsCorrectly() {
        String accents = "My name is ÉùàçØöğ";
        String token = Normalizer.normalize(accents);
        assertThat(token).isEqualTo("mynameieuacøog");
    }


    @Test
    public void shouldNormalizeNonLatinCorrectly() {
        // Those are random character sequences. It would be totally unintentional if anything was offensive.
        assertThat(Normalizer.normalize("La")).isEqualTo("la"); // Latin
        assertThat(Normalizer.normalize("αΨ")).isEqualTo("aps"); // Greek
        assertThat(Normalizer.normalize("Жц")).isEqualTo("zc"); // Cyrillic
        assertThat(Normalizer.normalize("թխ")).isEqualTo("tx"); // Armenian
        //FIXME PROBLEM
        assertThat(Normalizer.normalize("جؼ")).isEqualTo("jؼ"); // Arabic
        assertThat(Normalizer.normalize("שמ")).isEqualTo("sm"); // Hebrew
        assertThat(Normalizer.normalize("葵袷")).isEqualTo("kuijia"); // Kanji
        assertThat(Normalizer.normalize("ばふ")).isEqualTo("bafu"); // Hiragana
        assertThat(Normalizer.normalize("하함")).isEqualTo("haham"); // Korean
        assertThat(Normalizer.normalize("腌盒")).isEqualTo("yanhe"); // Chinese
        assertThat(Normalizer.normalize("บพ")).isEqualTo("bf"); // Thai
        assertThat(Normalizer.normalize("ങജ")).isEqualTo("naja"); // Malayalam
        assertThat(Normalizer.normalize("ছয়")).isEqualTo("shaya"); // Bengali
        assertThat(Normalizer.normalize("ఞఌ")).isEqualTo("nal"); // Telugu

        // All at once
        String nonLatin = "αψ жц թխ  جؼ שמ 葵袷 ばふ 하함 腌盒 บพ ങജ ছয় ఞఌ";
        assertThat(Normalizer.normalize(nonLatin)).isEqualTo("apszctxjؼsmkuijiabafuhahamyanhebfnajashayanal");
    }

    @Test
    public void shouldSkipPunctution() {
        String punctuation = "Foo&\"'(-_)=~#{[|`\\^@]}bAr£$¤+-*/=!?,;.:/§<>baZ";
        String token = Normalizer.normalize(punctuation);
        assertThat(token).isEqualTo("fobarbaz");
    }

    @Test
    public void shouldKeepEmojis() {
        String emoji = "foo ♥ \uD83E\uDD50 bar"; // "foo ♥ 🥐 bar"
        String token = Normalizer.normalize(emoji);
        assertThat(token).isEqualTo("fo♥\uD83E\uDD50bar");
    }
}

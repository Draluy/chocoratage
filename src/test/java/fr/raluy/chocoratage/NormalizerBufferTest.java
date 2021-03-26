package fr.raluy.chocoratage;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NormalizerBufferTest {

    KeyBuffer keysBuffer = new KeyBuffer();
    Matcher matcher = Matcher.NORMALIZER;

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
    public void shouldMatchApproiximateWoords() {
        keysBuffer.submitString("choKo ");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("choco"))));
    }

    @Test
    public void shouldMatchNoSpaces() {
        keysBuffer.clear();
        keysBuffer.submitString("painauchoc");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("pain au choc"))));
    }

    @Test
    public void shouldMatchPlurals() {
        keysBuffer.submitString("croissanst");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("croissants"))));
        assertFalse(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("decroissants"))));
    }

    @Test
    public void shouldMatchFarmacy() {
        keysBuffer.submitString("farmacy");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("Parmacy"))));
    }

    @Test
    public void shouldMatchWithAccents() {
        keysBuffer.submitString("Petit déj");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("PETIT DEJ"))));
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("petit déj"))));
    }

    @Test
    public void shouldMatchWithAccents2() {
        keysBuffer.submitString("peTIT     dEj");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("petit dej"))));
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("PETIT DÉJ"))));

        keysBuffer.clear();
        keysBuffer.submitString("peTITdEj");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("petit dej"))));
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("PETIT DÉJ"))));

        keysBuffer.clear();
        keysBuffer.submitString("p.e.t.i.t.d.é.j");
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("petit dej"))));
        assertTrue(matcher.matches(keysBuffer, singleton(new ForbiddenPhrase("PETIT DÉJ"))));
    }
}

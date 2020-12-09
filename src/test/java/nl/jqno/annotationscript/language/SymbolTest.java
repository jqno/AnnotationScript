package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SymbolTest {
    @Test
    public void name() {
        var sut = new Symbol("some-name");
        assertEquals("some-name", sut.name);
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(Symbol.class).verify();
    }

    @Test
    public void tostring() {
        var sut = new Symbol("some-name");
        assertEquals("'some-name", sut.toString());
    }
}

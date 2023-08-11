package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SymbolTest {
    @Test
    public void name() {
        var sut = new Symbol("some-name");
        assertEquals("some-name", sut.name());
    }

    @Test
    public void tostring() {
        var sut = new Symbol("some-name");
        assertEquals("'some-name", sut.toString());
    }
}

package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AstSymbolTest {
    @Test
    public void value() {
        var sut = new AstSymbol("some-value");
        assertEquals("some-value", sut.value());
    }

    @Test
    public void tostring() {
        var sut = new AstSymbol("some-value");
        assertEquals("some-value", sut.toString());
    }
}

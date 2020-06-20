package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AstIntTest {
    @Test
    public void value() {
        var sut = new AstInt(42);
        assertEquals(42, sut.value());
        assertEquals(42, sut.asInt());
    }

    @Test
    public void tostring() {
        var sut = new AstInt(42);
        assertEquals("42", sut.toString());
    }
}

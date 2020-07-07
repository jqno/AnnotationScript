package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AstStringTest {
    @Test
    public void value() {
        var sut = new AstString("some-value");
        assertEquals("'some-value'", sut.value());
        assertEquals("'some-value'", sut.asString());
    }

    @Test
    public void tostring() {
        var sut = new AstString("some-value");
        assertEquals("'some-value'", sut.toString());
    }
}

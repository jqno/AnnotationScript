package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AstFloatTest {
    @Test
    public void value() {
        var sut = new AstFloat(1.2);
        assertEquals(1.2, sut.value(), 0.0001);
        assertEquals(1.2, sut.asFloat(), 0.0001);
    }

    @Test
    public void tostring() {
        var sut = new AstFloat(1.2);
        assertEquals("1.2", sut.toString());
    }
}

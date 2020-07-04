package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.language.ast.AstInt;

public class ExpTest {
    @Test
    public void value() {
        var sut = new Exp(new AstInt(42));
        var actual = sut.value();
        assertEquals(new AstInt(42), actual);
    }
}

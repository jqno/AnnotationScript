package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ValueTest {
    @Test
    public void value() {
        var sut = new Value(42);
        var actual = sut.value();
        assertEquals(42, actual);
    }
}

package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ValueTest {
    private final Value sut = new Value(42);

    @Test
    public void isProcedure() {
        assertFalse(sut.isProcedure());
    }

    @Test
    public void value() {
        var actual = sut.value();
        assertEquals(42, actual);
    }
}

package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class FnTest {
    private final Fn sut = new Fn() {
        @Override
        public boolean isProcedure() {
            return false;
        }
    };

    @Test
    public void value() {
        var e = assertThrows(EvaluationException.class, () -> sut.value());
        assertEquals("This Fn is not a value", e.getMessage());
    }

    @Test
    public void evaluate() {
        var e = assertThrows(EvaluationException.class, () -> sut.evaluate(null, null, null));
        assertEquals("This Fn is not a builtin or lambda", e.getMessage());
    }
}

package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.language.ast.AstExpTest.AstBogus;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.equalsverifier.EqualsVerifier;

public class AstExpTest {
    private final AstExp sut = new AstBogus();

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forPackage(AstExp.class.getPackageName())
            .except(AstBogus.class)
            .verify();
    }

    @Test
    public void asIntThrows() {
        var e = assertThrows(EvaluationException.class, () -> sut.asInt());
        assertEquals("not an int: X", e.getMessage());
    }

    @Test
    public void asFloatThrows() {
        var e = assertThrows(EvaluationException.class, () -> sut.asFloat());
        assertEquals("not a float: X", e.getMessage());
    }

    @Test
    public void asStringThrows() {
        var e = assertThrows(EvaluationException.class, () -> sut.asString());
        assertEquals("not a string: X", e.getMessage());
    }

    @Test
    public void asSymbolThrows() {
        var e = assertThrows(EvaluationException.class, () -> sut.asSymbol());
        assertEquals("not a symbol: X", e.getMessage());
    }

    public static final class AstBogus implements AstExp {
        @Override
        public Object value() {
            return "X";
        }
    }
}

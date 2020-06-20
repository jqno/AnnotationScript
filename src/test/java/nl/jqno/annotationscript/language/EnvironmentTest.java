package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.language.ast.AstFloat;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class EnvironmentTest {

    private static final Map<String, Fn> ENV = HashMap.of(
        "pi", new Fn("pi", 0, params -> new AstFloat(Math.PI))
    );

    @Test
    public void successfulLookup() {
        var sut = new Environment(ENV);
        var actual = sut.lookup("pi");
        var expected = ENV.get("pi").get();
        assertEquals(expected, actual);
    }

    @Test
    public void throwsIfSymbolIsAbsent() {
        var sut = new Environment(ENV);
        var e = assertThrows(EvaluationException.class, () ->
            sut.lookup("something-else")
        );
        assertEquals("unknown symbol: something-else", e.getMessage());
    }
}

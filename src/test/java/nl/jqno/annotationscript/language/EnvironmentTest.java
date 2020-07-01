package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.language.ast.AstFloat;
import nl.jqno.annotationscript.language.ast.AstInt;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class EnvironmentTest {

    private static final Map<String, Fn> ENV = HashMap.of(
        "pi", Fn.value(new AstFloat(Math.PI))
    );

    @Test
    public void successfulLookup() {
        var sut = new Environment(ENV);
        var actual = sut.lookup("pi");
        var expected = ENV.get("pi").get();
        assertEquals(expected, actual);
    }

    @Test
    public void lookupThrowsIfSymbolIsAbsent() {
        var sut = new Environment(ENV);
        var e = assertThrows(EvaluationException.class, () ->
            sut.lookup("something-else")
        );
        assertEquals("unknown symbol: something-else", e.getMessage());
    }

    @Test
    public void successfulAdd() {
        var sut = new Environment(ENV);
        var actual = sut.add("r", Fn.value(new AstInt(10)));
        assertEquals(new AstInt(10), actual.lookup("r").evaluate(List.empty()));
    }

    @Test
    public void successfulMerge() {
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of("e", Fn.value(new AstFloat(Math.E))));
        var merged = sut.merge(otherEnv);
        assertNotNull(merged.lookup("pi"));
        assertNotNull(merged.lookup("e"));
    }

    @Test
    public void successfulMergeWhereThisOverridesThat() {
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of("pi", Fn.value(new AstFloat(Math.E))));
        var merged = sut.merge(otherEnv);
        var pi = merged.lookup("pi");
        assertEquals(new AstFloat(Math.PI), pi.evaluate(List.empty()));
    }
}

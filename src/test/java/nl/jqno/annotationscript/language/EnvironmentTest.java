package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.annotationscript.language.fn.Fn;

public class EnvironmentTest {

    private static final Symbol PI = new Symbol("pi");
    private static final Map<Symbol, Fn> ENV = HashMap.of(
        PI, Fn.val("pi", Math.PI)
    );

    @Test
    public void successfulLookupOption() {
        var sut = new Environment(ENV);
        var actual = sut.lookupOption(PI).get();
        var expected = ENV.get(PI).get();
        assertEquals(expected, actual);
    }

    @Test
    public void lookupOptionReturnsNoneIfSymbolIsAbsent() {
        var sut = new Environment(ENV);
        var actual = sut.lookupOption(new Symbol("something-else"));
        assertEquals(Option.none(), actual);
    }

    @Test
    public void successfulLookup() {
        var sut = new Environment(ENV);
        var actual = sut.lookup(PI);
        var expected = ENV.get(PI).get();
        assertEquals(expected, actual);
    }

    @Test
    public void lookupThrowsIfSymbolIsAbsent() {
        var sut = new Environment(ENV);
        var e = assertThrows(EvaluationException.class, () ->
            sut.lookup(new Symbol("something-else"))
        );
        assertEquals("unknown symbol: 'something-else", e.getMessage());
    }

    @Test
    public void successfulAdd() {
        var r = new Symbol("r");
        var sut = new Environment(ENV);
        var actual = sut.add(r, Fn.val("r", 10));
        assertEquals(10, actual.lookup(r).value());
    }

    @Test
    public void successfulMerge() {
        var e = new Symbol("e");
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of(e, Fn.val("e", Math.E)));
        var merged = sut.merge(otherEnv);
        assertNotNull(merged.lookup(PI));
        assertNotNull(merged.lookup(e));
    }

    @Test
    public void successfulMergeWhereThisOverridesThat() {
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of(PI, Fn.val("e", Math.E)));
        var merged = sut.merge(otherEnv);
        var pi = merged.lookup(PI);
        assertEquals(Math.PI, pi.value());
    }
}

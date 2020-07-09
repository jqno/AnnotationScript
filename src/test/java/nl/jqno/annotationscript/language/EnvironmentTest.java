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

    private static final Map<String, Fn> ENV = HashMap.of(
        "pi", Fn.val("pi", Math.PI)
    );

    @Test
    public void successfulLookupOption() {
        var sut = new Environment(ENV);
        var actual = sut.lookupOption("pi").get();
        var expected = ENV.get("pi").get();
        assertEquals(expected, actual);
    }

    @Test
    public void lookupOptionReturnsNoneIfSymbolIsAbsent() {
        var sut = new Environment(ENV);
        var actual = sut.lookupOption("something-else");
        assertEquals(Option.none(), actual);
    }

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
        var actual = sut.add("r", Fn.val("r", 10));
        assertEquals(10, actual.lookup("r").value());
    }

    @Test
    public void successfulMerge() {
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of("e", Fn.val("e", Math.E)));
        var merged = sut.merge(otherEnv);
        assertNotNull(merged.lookup("pi"));
        assertNotNull(merged.lookup("e"));
    }

    @Test
    public void successfulMergeWhereThisOverridesThat() {
        var sut = new Environment(ENV);
        var otherEnv = new Environment(HashMap.of("pi", Fn.val("e", Math.E)));
        var merged = sut.merge(otherEnv);
        var pi = merged.lookup("pi");
        assertEquals(Math.PI, pi.value());
    }
}

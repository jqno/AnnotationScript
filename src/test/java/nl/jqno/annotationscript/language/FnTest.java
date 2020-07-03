package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class FnTest {
    @Test
    public void appliesTheValue() {
        var sut = new Fn(params -> 42);
        var actual = sut.evaluate(List.empty());
        assertEquals(42, actual);
    }

    @Test
    public void appliesTheProc() {
        var sut = new Fn(params -> params.foldLeft(0.0, (acc, curr) -> acc + (double)curr));
        var actual = sut.evaluate(List.of(1.0, 2.0, 3.0, 4.0));
        assertEquals(10.0, actual);
    }
}

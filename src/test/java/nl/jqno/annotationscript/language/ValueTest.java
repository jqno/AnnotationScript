package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class ValueTest {
    @Test
    public void appliesTheValue() {
        var sut = new Value(42);
        var actual = sut.value();
        assertEquals(42, actual);
    }
}

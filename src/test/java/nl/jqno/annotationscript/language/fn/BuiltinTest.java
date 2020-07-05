package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;

public class BuiltinTest {
    @Test
    public void evaluate() {
        var sut = new Builtin(params -> params.foldLeft(0.0, (acc, curr) -> acc + (double)curr));
        var actual = sut.evaluate(List.of(1.0, 2.0, 3.0, 4.0), new Environment(HashMap.empty()), null);
        assertEquals(10.0, actual);
    }
}

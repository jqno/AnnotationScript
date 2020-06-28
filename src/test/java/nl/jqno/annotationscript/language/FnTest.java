package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class FnTest {
    @Test
    public void appliesTheValue() {
        var sut = Fn.value(new AstInt(42));
        var actual = sut.evaluate(List.empty());
        assertEquals(new AstInt(42), actual);
        assertFalse(sut.isProc());
    }
    @Test
    public void appliesTheProc() {
        var sut = Fn.proc(params -> new AstFloat(params.foldLeft(0.0, (acc, curr) -> acc + curr.asFloat())));
        var actual = sut.evaluate(List.of(new AstFloat(1), new AstFloat(2), new AstFloat(3), new AstFloat(4)));
        assertEquals(new AstFloat(10.0), actual);
        assertTrue(sut.isProc());
    }
}

package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class FnTest {
    @Test
    public void zeroArityFnIsEvaluatedCorrectly() {
        var sut = new Fn("pi", 0, params -> new AstFloat(Math.PI));
        assertEquals(new AstFloat(Math.PI), sut.evaluate(List.empty()));
    }

    @Test
    public void singleArityFnIsEvaluatedCorrectly() {
        var sut = new Fn("abs", 1, params -> new AstInt(Math.abs(params.get(0).asInt())));
        assertEquals(new AstInt(42), sut.evaluate(List.of(new AstInt(-42))));
    }

    @Test
    public void doubleArityFnIsEvaluatedCorrectly() {
        var sut = new Fn(">", 2, params -> new AstInt(params.get(0).asFloat() > params.get(1).asFloat() ? 1 : 0));
        assertEquals(new AstInt(0), sut.evaluate(List.of(new AstInt(11), new AstInt(42))));
    }

    @Test
    public void variableArityFnIsEvaluatedCorrectly() {
        var sut = new Fn("+", Fn.VARARG, params -> new AstFloat(params.foldLeft(0.0, (acc, curr) -> acc + curr.asFloat())));
        assertEquals(new AstFloat(1), sut.evaluate(List.of(new AstInt(1))));
        assertEquals(new AstFloat(42), sut.evaluate(List.of(new AstInt(3), new AstInt(33), new AstInt(6))));
    }

    @Test
    public void incorrectArityThrows() {
        var sut = new Fn("pi", 0, params -> new AstFloat(Math.PI));
        var e = assertThrows(EvaluationException.class, () ->
            sut.evaluate(List.of(new AstSymbol("booboo")))
        );
        assertEquals("invalid number of parameters to pi", e.getMessage());
    }

    @Test
    public void incorrectVariableArityThrows() {
        var sut = new Fn("head", Fn.VARARG, params -> params.head());
        var e = assertThrows(EvaluationException.class, () ->
            sut.evaluate(List.empty())
        );
        assertEquals("invalid number of parameters to head", e.getMessage());
    }
}

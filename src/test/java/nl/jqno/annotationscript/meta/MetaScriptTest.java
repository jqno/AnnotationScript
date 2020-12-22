package nl.jqno.annotationscript.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Symbol;

public class MetaScriptTest {
    @Test
    public void addition() {
        var input= "(add1 42)";
        var actual = MetaScript.run(input);
        assertEquals(43, actual);
    }

    @Test
    public void quote() {
        var input = "(quote (a b))";
        var actual = MetaScript.run(input);
        assertEquals(List.of(new Symbol("a"), new Symbol("b")), actual);
    }

    @Test
    public void car() {
        var input = "(car (quote (1 2 3)))";
        var actual = MetaScript.run(input);
        assertEquals(1, actual);
    }

    @Test
    public void lists() {
        var input = "(cons (car (quote (1 2 3))) (cdr (quote (a b c))))";
        var actual = MetaScript.run(input);
        assertEquals(List.of(1, new Symbol("b"), new Symbol("c")), actual);
    }

    @Test
    public void lambdaWithBooleans() {
        var input = "((lambda (x) (cond (x (quote true)) (else (quote false)))) #t)";
        var actual = MetaScript.run(input);
        assertEquals(new Symbol("true"), actual);
    }

    @Test
    public void definePrimitive() {
        var input = "(define (r 10) (add1 r))";
        var actual = MetaScript.run(input);
        assertEquals(11, actual);
    }
}

package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.annotationscript.language.fn.Fn;

public class EvaluatorTest {

    private final Evaluator sut = new Evaluator();
    private final Environment env = new Environment(HashMap.of(
        new Symbol("begin"), Fn.builtin("begin", params -> params.last()),
        new Symbol("pi"), Fn.val("pi", Math.PI),
        new Symbol("+"), Fn.builtin("+", params -> params.foldLeft(0.0, (acc, curr) -> acc + Double.valueOf(curr.toString())))
    ));

    @Test
    public void successfullyEvaluateAnInt() {
        var actual = sut.eval(42, env);
        assertEquals(42, actual);
    }

    @Test
    public void successfullyEvaluateAFloat() {
        var actual = sut.eval(3.14, env);
        assertEquals(3.14, actual);
    }

    @Test
    public void successfullyEvaluateAString() {
        var actual = sut.eval("yeah", env);
        assertEquals("yeah", actual);
    }

    @Test
    public void successfullyEvaluateASymbol() {
        var actual = sut.eval(new Symbol("pi"), env);
        assertEquals(Math.PI, actual);
    }

    @Test
    public void successfullyEvaluateIf1() {
        var actual = sut.eval(List.of(new Symbol("if"), 1, 1, 2), env);
        assertEquals(1, actual);
    }

    @Test
    public void successfullyEvaluateIfWhatever() {
        var actual = sut.eval(List.of(new Symbol("if"), new Symbol("pi"), 1, 2), env);
        assertEquals(1, actual);
    }

    @Test
    public void successfullyEvaluateIf0() {
        var actual = sut.eval(List.of(new Symbol("if"), 0, 1, 2), env);
        assertEquals(2, actual);
    }

    @Test
    public void successfullyEvaluateIf0Point0() {
        var actual = sut.eval(List.of(new Symbol("if"), 0.0, 1, 2), env);
        assertEquals(2, actual);
    }

    @Test
    public void successfullyEvaluateIfWithSymbols() {
        var actual = sut.eval(List.of(new Symbol("if"), 1, new Symbol("pi"), new Symbol("pi")), env);
        assertEquals(Math.PI, actual);
    }

    @Test
    public void successfullyEvaluateSimpleCond() {
        var actual = sut.eval(List.of(new Symbol("cond"), 1, 42), env);
        assertEquals(42, actual);
    }

    @Test
    public void successfullyEvaluateBigCond() {
        var expression = List.of(
            new Symbol("cond"),
            0, 1,
            0.0, 2,
            List.of(new Symbol("+"), 0, 0), 3,
            "yeah", 4,
            0, 5);
        var actual = sut.eval(expression, env);
        assertEquals(4, actual);
    }

    @Test
    public void throwWhenCondHasNoTrueBranch() {
        var expression = List.of(
            new Symbol("cond"),
            0, 1,
            0.0, 2);
        var e = assertThrows(EvaluationException.class, () -> sut.eval(expression, env));
        assertEquals("cond has no true branch", e.getMessage());
    }

    @Test
    public void successfullyEvaluateDefine() {
        var actual = sut.eval(List.of(
            new Symbol("begin"),
            List.of(new Symbol("define"), new Symbol("x"), 42),
            new Symbol("x")), env);
        assertEquals(42, actual);
    }

    @Test
    public void successfullyEvaluateDefinedLambda() {
        // (begin
        //   (define add-two (lambda (n) (+ n 2)))
        //   (add-two 4))
        var program = List.of(
            new Symbol("begin"),
            List.of(
                new Symbol("define"),
                new Symbol("add-two"),
                List.of(
                    new Symbol("lambda"),
                    List.of(new Symbol("n")),
                    List.of(new Symbol("+"), new Symbol("n"), 2))),
            List.of(new Symbol("add-two"), 4));
        var actual = sut.eval(program, env);
        assertEquals(6.0, actual);
    }

    @Test
    public void successfullyEvaluateLambdaInvocation() {
        // (+ ((lambda (n) (+ n 2)) 4) 10)
        var program = List.of(
            new Symbol("+"),
            List.of(
                List.of(
                    new Symbol("lambda"),
                    List.of(new Symbol("n")),
                    List.of(new Symbol("+"), new Symbol("n"), 2)),
                4),
            10);
        var actual = sut.eval(program, env);
        assertEquals(16.0, actual);
    }

    @Test
    public void successfullyEvaluateLambdaInHigherOrderFunction() {
        // (begin
        //   (define apply (lambda (f x y) (f x y)))
        //   (apply + 1 2))
        var program = List.of(
                new Symbol("begin"),
                List.of(
                    new Symbol("define"),
                    new Symbol("apply"),
                    List.of(
                        new Symbol("lambda"),
                        List.of(new Symbol("f"), new Symbol("x"), new Symbol("y")),
                        List.of(new Symbol("f"), new Symbol("x"), new Symbol("y")))),
                List.of(new Symbol("apply"), new Symbol("+"), 1, 2));
        var actual = sut.eval(program, env);
        assertEquals(3.0, actual);
    }

    @Test
    public void successfullyEvaluateQuote() {
        var expression = List.of(new Symbol("if"), 1, 2, 3);
        var quotedExpression = List.of(new Symbol("quote"), expression);
        var actual = sut.eval(quotedExpression, env);
        assertEquals(expression, actual);
    }

    @Test
    public void successfullyEvaluateEmptyList() {
        var actual = sut.eval(List.empty(), env);
        assertEquals(List.empty(), actual);
    }

    @Test
    public void successfullyEvaluateProc() {
        var actual = sut.eval(List.of(new Symbol("+"), 1, 2, 3, 4), env);
        assertEquals(10.0, actual);
    }

    @Test
    public void successfullyEvaluateProcWhereProcNameMustBeEvaluatedFirst() {
        var expression = List.of(
            List.of(new Symbol("if"), 1, new Symbol("+"), new Symbol("*")),
            4,
            2);
        var actual = sut.eval(expression, env);
        assertEquals(6.0, actual);
    }

    @Test
    public void successfullyEvaluateProcWhereProcNameMustBeEvaluatedFirstRecursively() {
        var expression = List.of(
            List.of(new Symbol("if"), 1,
                List.of(new Symbol("if"), 1, new Symbol("+"), new Symbol("-")),
                List.of(new Symbol("if"), 0, new Symbol("*"), new Symbol("/"))),
            4,
            2);
        var actual = sut.eval(expression, env);
        assertEquals(6.0, actual);
    }
}

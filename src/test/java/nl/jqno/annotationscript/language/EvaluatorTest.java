package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;
import nl.jqno.annotationscript.language.fn.Fn;

public class EvaluatorTest {

    private final Evaluator sut = new Evaluator();
    private final Environment env = new Environment(HashMap.of(
        "begin", Fn.builtin(params -> params.last()),
        "pi", Fn.val(Math.PI),
        "+", Fn.builtin(params -> params.foldLeft(0.0, (acc, curr) -> acc + Double.valueOf(curr.toString())))
    ));

    @Test
    public void successfullyEvaluateAnInt() {
        var actual = sut.eval(new AstInt(42), env);
        assertEquals(42, actual);
    }

    @Test
    public void successfullyEvaluateAFloat() {
        var actual = sut.eval(new AstFloat(3.14), env);
        assertEquals(3.14, actual);
    }

    @Test
    public void successfullyEvaluateAString() {
        var actual = sut.eval(new AstString("yeah"), env);
        assertEquals("'yeah'", actual);
    }

    @Test
    public void successfullyEvaluateASymbol() {
        var actual = sut.eval(new AstSymbol("pi"), env);
        assertEquals(Math.PI, actual);
    }

    @Test
    public void successfullyEvaluateIf1() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(1), new AstInt(1), new AstInt(2)), env);
        assertEquals(1, actual);
    }

    @Test
    public void successfullyEvaluateIfWhatever() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstSymbol("pi"), new AstInt(1), new AstInt(2)), env);
        assertEquals(1, actual);
    }

    @Test
    public void successfullyEvaluateIf0() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(0), new AstInt(1), new AstInt(2)), env);
        assertEquals(2, actual);
    }

    @Test
    public void successfullyEvaluateIfWithSymbols() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(1), new AstSymbol("pi"), new AstSymbol("pi")), env);
        assertEquals(Math.PI, actual);
    }

    @Test
    public void successfullyEvaluateDefine() {
        var actual = sut.eval(new AstList(
            new AstSymbol("begin"),
            new AstList(new AstSymbol("define"), new AstSymbol("x"), new AstInt(42)),
            new AstSymbol("x")), env);
        assertEquals(42, actual);
    }

    @Test
    public void successfullyEvaluateDefinedLambda() {
        // (begin
        //   (define add-two (lambda (n) (+ n 2)))
        //   (add-two 4))
        var program = new AstList(
            new AstSymbol("begin"),
            new AstList(
                new AstSymbol("define"),
                new AstSymbol("add-two"),
                new AstList(
                    new AstSymbol("lambda"),
                    new AstList(new AstSymbol("n")),
                    new AstList(new AstSymbol("+"), new AstSymbol("n"), new AstInt(2)))),
            new AstList(new AstSymbol("add-two"), new AstInt(4)));
        var actual = sut.eval(program, env);
        assertEquals(6.0, actual);
    }

    @Test
    public void successfullyEvaluateLambdaInvocation() {
        // (+ ((lambda (n) (+ n 2)) 4) 10)
        var program = new AstList(
            new AstSymbol("+"),
            new AstList(
                new AstList(
                    new AstSymbol("lambda"),
                    new AstList(new AstSymbol("n")),
                    new AstList(new AstSymbol("+"), new AstSymbol("n"), new AstInt(2))),
                new AstInt(4)),
            new AstInt(10));
        var actual = sut.eval(program, env);
        assertEquals(16.0, actual);
    }

    @Test
    public void successfullyEvaluateLambdaInHigherOrderFunction() {
        // (begin
        //   (define apply (lambda (f x y) (f x y)))
        //   (apply + 1 2))
        var program = new AstList(
                new AstSymbol("begin"),
                new AstList(
                    new AstSymbol("define"),
                    new AstSymbol("apply"),
                    new AstList(
                        new AstSymbol("lambda"),
                        new AstList(new AstSymbol("f"), new AstSymbol("x"), new AstSymbol("y")),
                        new AstList(new AstSymbol("f"), new AstSymbol("x"), new AstSymbol("y")))),
                new AstList(new AstSymbol("apply"), new AstSymbol("+"), new AstInt(1), new AstInt(2)));
        var actual = sut.eval(program, env);
        assertEquals(3.0, actual);
    }

    @Test
    public void successfullyEvaluateQuote() {
        var expression = new AstList(new AstSymbol("if"), new AstInt(1), new AstInt(2), new AstInt(3));
        var quotedExpression = new AstList(new AstSymbol("quote"), expression);
        var actual = sut.eval(quotedExpression, env);
        assertEquals(expression, actual);
    }

    @Test
    public void successfullyEvaluateProcedurep() {
        assertEquals(1, sut.eval(new AstList(new AstSymbol("procedure?"), new AstSymbol("begin")), env));
        assertEquals(1, sut.eval(new AstList(new AstSymbol("procedure?"), new AstSymbol("+")), env));
        assertEquals(0, sut.eval(new AstList(new AstSymbol("procedure?"), new AstSymbol("pi")), env));
        assertEquals(0, sut.eval(new AstList(new AstSymbol("procedure?"), new AstSymbol("undefined-symbol")), env));
        assertEquals(0, sut.eval(new AstList(new AstSymbol("procedure?"), new AstInt(0)), env));
        assertEquals(0, sut.eval(new AstList(new AstSymbol("procedure?"), new AstString("not-a-symbol")), env));
    }

    @Test
    public void successfullyEvaluateEmptyList() {
        var actual = sut.eval(new AstList(List.empty()), env);
        assertEquals(List.empty(), actual);
    }

    @Test
    public void successfullyEvaluateProc() {
        var actual = sut.eval(new AstList(new AstSymbol("+"), new AstInt(1), new AstInt(2), new AstInt(3), new AstInt(4)), env);
        assertEquals(10.0, actual);
    }

    @Test
    public void successfullyEvaluateProcWhereProcNameMustBeEvaluatedFirst() {
        var expression = new AstList(
            new AstList(new AstSymbol("if"), new AstInt(1), new AstSymbol("+"), new AstSymbol("*")),
            new AstInt(4),
            new AstInt(2));
        var actual = sut.eval(expression, env);
        assertEquals(6.0, actual);
    }

    @Test
    public void successfullyEvaluateProcWhereProcNameMustBeEvaluatedFirstRecursively() {
        var expression = new AstList(
            new AstList(new AstSymbol("if"), new AstInt(1),
                new AstList(new AstSymbol("if"), new AstInt(1), new AstSymbol("+"), new AstSymbol("-")),
                new AstList(new AstSymbol("if"), new AstInt(0), new AstSymbol("*"), new AstSymbol("/"))),
            new AstInt(4),
            new AstInt(2));
        var actual = sut.eval(expression, env);
        assertEquals(6.0, actual);
    }
}

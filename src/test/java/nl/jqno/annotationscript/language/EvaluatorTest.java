package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class EvaluatorTest {

    private final Evaluator sut = new Evaluator();
    private final Environment env = new Environment(HashMap.of(
        "begin", Fn.proc(params -> params.last()),
        "pi", Fn.value(new AstFloat(Math.PI)),
        "+", Fn.proc(params -> new AstFloat(params.foldLeft(0.0, (acc, curr) -> acc + curr.asFloat())))
    ));

    @Test
    public void successfullyEvaluateAnInt() {
        var actual = sut.eval(new AstInt(42), env);
        assertEquals(new AstInt(42), actual);
    }

    @Test
    public void successfullyEvaluateAFloat() {
        var actual = sut.eval(new AstFloat(3.14), env);
        assertEquals(new AstFloat(3.14), actual);
    }

    @Test
    public void successfullyEvaluateAString() {
        var actual = sut.eval(new AstString("yeah"), env);
        assertEquals(new AstString("yeah"), actual);
    }

    @Test
    public void successfullyEvaluateASymbol() {
        var actual = sut.eval(new AstSymbol("pi"), env);
        assertEquals(new AstFloat(Math.PI), actual);
    }

    @Test
    public void successfullyEvaluateIf1() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(1), new AstInt(1), new AstInt(2)), env);
        assertEquals(new AstInt(1), actual);
    }

    @Test
    public void successfullyEvaluateIfWhatever() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstSymbol("pi"), new AstInt(1), new AstInt(2)), env);
        assertEquals(new AstInt(1), actual);
    }

    @Test
    public void successfullyEvaluateIf0() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(0), new AstInt(1), new AstInt(2)), env);
        assertEquals(new AstInt(2), actual);
    }

    @Test
    public void successfullyEvaluateIfWithSymbols() {
        var actual = sut.eval(new AstList(new AstSymbol("if"), new AstInt(1), new AstSymbol("pi"), new AstSymbol("pi")), env);
        assertEquals(new AstFloat(Math.PI), actual);
    }

    @Test
    public void successfullyEvaluateDefine() {
        var actual = sut.eval(new AstList(
            new AstSymbol("begin"),
            new AstList(new AstSymbol("define"), new AstSymbol("x"), new AstInt(42)),
            new AstSymbol("x")), env);
        assertEquals(new AstInt(42), actual);
    }

    @Test
    public void successfullyEvaluateQuote() {
        var expression = new AstList(new AstSymbol("if"), new AstInt(1), new AstInt(2), new AstInt(3));
        var quotedExpression = new AstList(new AstSymbol("quote"), expression);
        var actual = sut.eval(quotedExpression, env);
        assertEquals(expression, actual);
    }

    @Test
    public void successfullyEvaluateEmptyList() {
        var actual = sut.eval(new AstList(List.empty()), env);
        assertEquals(new AstList(List.empty()), actual);
    }

    @Test
    public void successfullyEvaluateProc() {
        var actual = sut.eval(new AstList(new AstSymbol("+"), new AstInt(1), new AstInt(2), new AstInt(3), new AstInt(4)), env);
        assertEquals(new AstFloat(10), actual);
    }

    @Test
    public void successfullyEvaluateProcWhereProcNameMustBeEvaluatedFirst() {
        var expression = new AstList(
            new AstList(new AstSymbol("if"), new AstInt(1), new AstSymbol("+"), new AstSymbol("*")),
            new AstInt(4),
            new AstInt(2));
        var actual = sut.eval(expression, env);
        assertEquals(new AstFloat(6), actual);
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
        assertEquals(new AstFloat(6), actual);
    }
}

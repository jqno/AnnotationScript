package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.fn.Value;

public class GlobalEnvironmentTest {
    private static final Environment ENV = GlobalEnvironment.build();

    @Test
    public void addition() {
        assertEquals(1.0, evaluate("+", 1.0));
        assertEquals(42.0, evaluate("+", 3, 33, 6));
    }

    @Test
    public void subtraction() {
        assertEquals(1.0, evaluate("-", 1));
        assertEquals(10.0, evaluate("-", 42, 30, 2));
    }

    @Test
    public void multiplication() {
        assertEquals(1.0, evaluate("*", 1));
        assertEquals(24.0, evaluate("*", 2, 3, 4));
    }

    @Test
    public void division() {
        assertEquals(1.0, evaluate("/", 1));
        assertEquals(2.5, evaluate("/", 10, 2, 2));
    }

    @Test
    public void modulo() {
        assertEquals(1.0, evaluate("%", 1));
        assertEquals(2.0, evaluate("%", 17, 6, 3));
    }

    @Test
    public void greaterThan() {
        assertEquals(1, evaluate(">", 10, 4));
        assertEquals(0, evaluate(">", 10, 10));
        assertEquals(0, evaluate(">", 4, 10));
    }

    @Test
    public void lessThan() {
        assertEquals(1, evaluate("<", 4, 10));
        assertEquals(0, evaluate("<", 10, 10));
        assertEquals(0, evaluate("<", 10, 4));
    }

    @Test
    public void greaterThanOrEqualTo() {
        assertEquals(1, evaluate(">=", 10, 4));
        assertEquals(1, evaluate(">=", 10, 10));
        assertEquals(0, evaluate(">=", 4, 10));
    }

    @Test
    public void lessThanOrEqualTo() {
        assertEquals(1, evaluate("<=", 4, 10));
        assertEquals(1, evaluate("<=", 10, 10));
        assertEquals(0, evaluate("<=", 10, 4));
    }

    @Test
    public void equalTo() {
        assertEquals(1, evaluate("=", 10, 10));
        assertEquals(0, evaluate("=", 10, 4));
        assertEquals(1, evaluate("=", "a", "a"));
        assertEquals(0, evaluate("=", "a", "b"));
        assertEquals(1, evaluate("=", "'a'", "'a'"));
        assertEquals(0, evaluate("=", "'a'", "'b'"));
    }

    @Test
    public void abs() {
        assertEquals(1.0, evaluate("abs", -1.0));
        assertEquals(1.0, evaluate("abs", 1.0));
    }

    @Test
    public void and() {
        assertEquals(1, evaluate("and", 1, 42));
        assertEquals(0, evaluate("and", 0, 42));
        assertEquals(0, evaluate("and", 1, 0));
        assertEquals(0, evaluate("and", 0, 0));

        assertEquals(1, evaluate("and", 1, 42, 1, 1));
        assertEquals(0, evaluate("and", 1, 42, 0, 1));
        assertEquals(0, evaluate("and", 0, 0, 0, 0));
    }

    @Test
    public void append() {
        assertEquals(List.of(1, 2, 3, 42), evaluate("append", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("append", 42, List.empty()));
    }

    @Test
    public void atomp() {
        assertEquals(1, evaluate("atom?", 42));
        assertEquals(1, evaluate("atom?", 42.0));
        assertEquals(1, evaluate("atom?", "'string'"));
        assertEquals(0, evaluate("atom?", "symbol"));
        assertEquals(0, evaluate("atom?", List.empty()));
    }

    @Test
    public void begin() {
        assertEquals(1, evaluate("begin", 1));
        assertEquals("z", evaluate("begin", "x", "y", "z"));
    }

    @Test
    public void cons() {
        assertEquals(List.of(42, 1, 2, 3), evaluate("cons", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("cons", 42, List.empty()));
    }

    @Test
    public void head() {
        assertEquals(1, evaluate("head", List.of(1, 2, 3)));
        assertEquals(null, evaluate("head", List.empty()));
        assertEquals(null, evaluate("head"));
        assertEquals(null, evaluate("head", "not-a-list"));
    }

    @Test
    public void length() {
        assertEquals(3, evaluate("length", List.of(1, 2, 3)));
        assertEquals(0, evaluate("length", List.empty()));
        assertNull(evaluate("length"));
        assertNull(evaluate("length", "not-a-list"));
    }

    @Test
    public void list() {
        assertEquals(List.of(1, 2, 3), evaluate("list", 1, 2, 3));
        assertEquals(List.empty(), evaluate("list"));
    }

    @Test
    public void listp() {
        assertEquals(1, evaluate("list?", List.of(1, 2, 3)));
        assertEquals(1, evaluate("list?", List.empty()));
        assertEquals(0, evaluate("list?", "not-a-list"));
        assertEquals(0, evaluate("list?", 1));
    }

    @Test
    public void max() {
        assertEquals(42.0, evaluate("max", 6, 42, 3));
        assertEquals(42.0, evaluate("max", 42));
    }

    @Test
    public void min() {
        assertEquals(3.0, evaluate("min", 6, 42, 3));
        assertEquals(42.0, evaluate("max", 42));
    }

    @Test
    public void not() {
        assertEquals(1, evaluate("not", 0));
        assertEquals(1, evaluate("not", 0.0));
        assertEquals(0, evaluate("not", 1));
        assertEquals(0, evaluate("not", 2));
        assertEquals(0, evaluate("not", "something"));
        assertEquals(0, evaluate("not", List.empty()));
    }

    @Test
    public void null_() {
        assertNull(evaluate("null"));
    }

    @Test
    public void nullp() {
        assertEquals(1, evaluate("null?", Option.none().getOrNull())); // avoid warnings caused by typing `null` directly
        assertEquals(0, evaluate("null?", "null"));
        assertEquals(0, evaluate("null?", 1));
    }

    @Test
    public void numberp() {
        assertEquals(1, evaluate("number?", 1));
        assertEquals(1, evaluate("number?", 1.0));
        assertEquals(0, evaluate("number?", "not-a-number"));
        assertEquals(0, evaluate("number?", "'not-a-number'"));
        assertEquals(0, evaluate("number?", List.empty()));
    }

    @Test
    public void or() {
        assertEquals(1, evaluate("or", 1, 42));
        assertEquals(1, evaluate("or", 0, 42));
        assertEquals(1, evaluate("or", 1, 0));
        assertEquals(0, evaluate("or", 0, 0));

        assertEquals(1, evaluate("or", 1, 42, 1, 1));
        assertEquals(1, evaluate("or", 0, 42, 0, 0));
        assertEquals(0, evaluate("or", 0, 0, 0, 0));
    }

    @Test
    public void pi() {
        assertEquals(Math.PI, evaluate("pi"));
    }

    @Test
    public void println() {
        assertNull(evaluate("println", "We're just going to assume", "that this actually printed something"));
    }

    @Test
    public void printlnErr() {
        assertNull(evaluate("println-err", "We're just going to assume", "that this actually printed something", "to System.err"));
    }

    @Test
    public void round() {
        assertEquals(42.0, evaluate("round", 42.42));
        assertEquals(42.0, evaluate("round", 41.52));
    }

    @Test
    public void stringp() {
        assertEquals(1, evaluate("string?", "'string'"));
        assertEquals(0, evaluate("string?", "'symbol"));
        assertEquals(0, evaluate("string?", "symbol'"));
        assertEquals(0, evaluate("string?", "symbol"));
        assertEquals(0, evaluate("string?", 42));
    }

    @Test
    public void symbolp() {
        assertEquals(1, evaluate("symbol?", "symbol"));
        assertEquals(1, evaluate("symbol?", "'symbol"));
        assertEquals(1, evaluate("symbol?", "symbol'"));
        assertEquals(0, evaluate("symbol?", "'string'"));
        assertEquals(0, evaluate("symbol?", 42));
    }

    @Test
    public void tail() {
        assertEquals(List.of(2, 3), evaluate("tail", List.of(1, 2, 3)));
        assertEquals(List.empty(), evaluate("tail", List.of(1)));
        assertEquals(List.empty(), evaluate("tail", List.empty()));
        assertEquals(null, evaluate("tail"));
        assertEquals(null, evaluate("tail", "not-a-list"));
    }

    private Object evaluate(String symbol, Object... params) {
        var sut = ENV.lookup(symbol);
        if (sut instanceof Value) {
            return sut.value();
        }
        return sut.evaluate(List.of(params), ENV, null);
    }
}

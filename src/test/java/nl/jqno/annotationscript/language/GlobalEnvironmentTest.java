package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
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
    public void begin() {
        assertEquals(1, evaluate("begin", 1));
        assertEquals("z", evaluate("begin", "x", "y", "z"));
    }

    @Test
    public void pi() {
        assertEquals(Math.PI, evaluate("pi"));
    }

    private Object evaluate(String symbol, Object... params) {
        var sut = ENV.lookup(symbol);
        if (sut instanceof Value) {
            return sut.value();
        }
        return sut.evaluate(List.of(params), ENV, null);
    }
}

package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class GlobalEnvironmentTest {
    private static final Environment ENV = GlobalEnvironment.build();

    @Test
    public void addition() {
        assertEquals(new AstFloat(1), evaluate("+", new AstInt(1)));
        assertEquals(new AstFloat(42), evaluate("+", new AstInt(3), new AstInt(33), new AstInt(6)));
    }

    @Test
    public void subtraction() {
        assertEquals(new AstFloat(1), evaluate("-", new AstInt(1)));
        assertEquals(new AstFloat(10), evaluate("-", new AstInt(42), new AstInt(30), new AstInt(2)));
    }

    @Test
    public void multiplication() {
        assertEquals(new AstFloat(1), evaluate("*", new AstInt(1)));
        assertEquals(new AstFloat(24), evaluate("*", new AstInt(2), new AstInt(3), new AstInt(4)));
    }

    @Test
    public void division() {
        assertEquals(new AstFloat(1), evaluate("/", new AstInt(1)));
        assertEquals(new AstFloat(2.5), evaluate("/", new AstInt(10), new AstInt(2), new AstInt(2)));
    }

    @Test
    public void begin() {
        assertEquals(new AstInt(1), evaluate("begin", new AstInt(1)));
        assertEquals(new AstSymbol("z"), evaluate("begin", new AstSymbol("x"), new AstSymbol("y"), new AstSymbol("z")));
    }

    @Test
    public void pi() {
        assertEquals(new AstFloat(Math.PI), evaluate("pi"));
    }

    private AstExp evaluate(String symbol, AstExp... params) {
        var sut = ENV.lookup(symbol);
        return sut.evaluate(List.of(params));
    }
}

package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;

public class AstLambdaTest {
    private static final List<AstSymbol> PARAMS = List.of(new AstSymbol("n"), new AstSymbol("m"));
    private static final AstExp BODY = new AstList(new AstSymbol("+"), new AstSymbol("n"), new AstSymbol("m"));
    private static final Environment ENV = new Environment(HashMap.empty());

    @Test
    public void value() {
        var sut = new AstLambda(PARAMS, BODY, ENV);
        assertNull(sut.value());
    }

    @Test
    public void tostring() {
        var sut = new AstLambda(PARAMS, BODY, ENV);
        assertEquals("[λ n,m]", sut.toString());
    }
}

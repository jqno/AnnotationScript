package nl.jqno.annotationscript.language.fn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.GlobalEnvironment;
import nl.jqno.annotationscript.language.ast.AstList;
import nl.jqno.annotationscript.language.ast.AstSymbol;

public class LambdaTest {
    private Lambda sut = null;

    @BeforeEach
    public void setUp() {
        var params = List.of(new AstSymbol("x"), new AstSymbol("y"));
        var body = new AstList(List.of(new AstSymbol("-"), new AstSymbol("x"), new AstSymbol("y")));
        var env = new Environment(HashMap.empty());

        sut = new Lambda(params, body, env);
    }

    @Test
    public void isProcedure() {
        assertTrue(sut.isProcedure());
    }

    @Test
    public void evaluate() {
        var arguments = List.<Object>of(10.0, 4.0);
        var actual = sut.evaluate(arguments, GlobalEnvironment.build(), new Evaluator());

        assertEquals(6.0, actual);
    }
}

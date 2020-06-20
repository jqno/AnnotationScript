package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class ParserTest {
    @Test
    public void parseValidTokens() {
        var input = List.of("(", "begin", "(", "define", "r", "10", ")", "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");
        var sut = new Parser(input);
        var actual = sut.parse();

        var expected = new AstList(List.of(
            new AstSymbol("begin"),
            new AstList(List.of(
                    new AstSymbol("define"),
                    new AstSymbol("r"),
                    new AstInt(10)
            )),
            new AstList(List.of(
                    new AstSymbol("*"),
                    new AstSymbol("pi"),
                    new AstList(List.of(
                            new AstSymbol("*"),
                            new AstSymbol("r"),
                            new AstSymbol("r")
                    ))
            ))
        ));

        assertEquals(expected, actual);
    }
}

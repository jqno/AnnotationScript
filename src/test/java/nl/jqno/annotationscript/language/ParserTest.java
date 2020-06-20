package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class ParserTest {
    @Test
    public void parseValidTokens() {
        var actual = parse("(", "begin", "(", "define", "r", "10", ")", "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");

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

    @Test
    public void throwsOnNoInput() {
        var e = assertThrows(ParseException.class, () ->
            parse()
        );
        assertEquals("no input", e.getMessage());
    }

    @Test
    public void throwsOnUnexpectedEndOfInput() {
        var e = assertThrows(ParseException.class, () ->
            parse("(", "begin")
        );
        assertEquals("unexpected EOF", e.getMessage());
    }

    @Test
    public void throwsOnUnexpectedCloseParen() {
        var e = assertThrows(ParseException.class, () ->
            parse(")")
        );
        assertEquals("unexpected )", e.getMessage());
    }

    @Test
    public void throwsOnNotAllInputConsumed() {
        var e = assertThrows(ParseException.class, () ->
            parse("(", "begin", ")", "stuff")
        );
        assertEquals("unexpected end of program", e.getMessage());
    }

    private AstExp parse(String... input) {
        var sut = new Parser(List.of(input));
        return sut.parse();
    }
}

package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.exceptions.ParseException;

public class ParserTest {
    @Test
    public void parseValidTokens() {
        var actual = parse("(", "begin", "(", "define", "r", "10", ")", "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");

        var expected = List.of(
            new Symbol("begin"),
            List.of(
                new Symbol("define"),
                new Symbol("r"),
                10),
            List.of(
                new Symbol("*"),
                new Symbol("pi"),
                List.of(
                    new Symbol("*"),
                    new Symbol("r"),
                    new Symbol("r"))));

        assertEquals(expected, actual);
    }

    @Test
    public void parseTypes() {
        var actual = parse("(", "a", "42", "1.2", "'str'", "'sym", "sym'", ")");

        var expected = List.of(
            new Symbol("a"),
            42,
            1.2,
            "str",
            new Symbol("'sym"),
            new Symbol("sym'"));

        assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyList() {
        var actual = parse("(", ")");
        var expected = List.empty();
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

    private Object parse(String... input) {
        var sut = new Parser(List.of(input));
        return sut.parse();
    }
}

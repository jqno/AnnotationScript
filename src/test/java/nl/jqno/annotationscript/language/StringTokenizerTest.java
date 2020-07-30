package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class StringTokenizerTest {
    @Test
    public void tokenizeValidInput() {
        var input = "(if 1 'one')";
        var sut = new StringTokenizer(input);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeValidInputWithAdditionalSpaces() {
        var input = " ( if   1  'one' ) ";
        var sut = new StringTokenizer(input);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeRecursiveValidInput() {
        var input = "(begin (define r 10) (* pi (* r r)))";
        var sut = new StringTokenizer(input);
        var actual = sut.tokenize();
        var expected = List.of("(", "begin", 
                "(", "define", "r", "10", ")",
                "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void tokenizeDeepInput() {
        var input = "(((((((((((😜)))))))))))";
        var sut = new StringTokenizer(input);
        var actual = sut.tokenize();
        var expected = List.of("(", "(", "(", "(", "(", "(", "(",
                "(",  "(", "(", "(", "😜", ")", ")", ")", ")",
                ")", ")", ")", ")", ")", ")", ")");
        assertEquals(expected, actual);
    }
}

package nl.jqno.annotationscript.abstract_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public abstract class StringTokenizerTest {

    public abstract List<String> tokenize(String input);

    @Test
    public void tokenizeValidInput() {
        var input = "(if 1 'one')";
        var actual = tokenize(input);
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeValidInputWithAdditionalSpaces() {
        var input = " ( if   1  'one' ) ";
        var actual = tokenize(input);
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeRecursiveValidInput() {
        var input = "(begin (define r 10) (* pi (* r r)))";
        var actual = tokenize(input);
        var expected = List.of("(", "begin", 
                "(", "define", "r", "10", ")",
                "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void tokenizeDeepInput() {
        var input = "(((((((((((😜)))))))))))";
        var actual = tokenize(input);
        var expected = List.of("(", "(", "(", "(", "(", "(", "(",
                "(",  "(", "(", "(", "😜", ")", ")", ")", ")",
                ")", ")", ")", ")", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void tokenizeInputWithStringsContainingSpaces() {
        var input = "(str/split '\\ ' input)";
        var actual = tokenize(input);
        var expected = List.of("(", "str/split", "' '", "input", ")");
        assertEquals(expected, actual);
    }
}

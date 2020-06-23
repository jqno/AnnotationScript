package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.Annotations.*;
import nl.jqno.annotationscript.language.exceptions.TokenizeException;

public class TokenizerTest {
    @Test
    public void tokenizeValidInput() {
        @One(literal="if")@One(literal="1")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", ")"), actual);
    }

    @Test
    public void tokenizeRecursiveValidInput() {
        @One(literal="begin")
        @One(list={@Two(literal="define"), @Two(literal="r"), @Two(literal="10")})
        @One(list={
            @Two(literal="*"),
            @Two(literal="pi"),
            @Two(list={@Three(literal="*"), @Three(literal="r"), @Three(literal="r")})})
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        var expected = List.of("(", "begin", 
                "(", "define", "r", "10", ")",
                "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void throwOnUninitializedAnnotation() {
        @One(literal="non-empty")@One
        class Input {}

        var sut = new Tokenizer(Input.class);
        var e = assertThrows(TokenizeException.class, () -> sut.tokenize());
        assertEquals("annotation has no value", e.getMessage());
    }

    @Test
    public void ignoreOtherAnnotations() {
        @One(literal="if")@IgnoreThis@One(literal="1")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", ")"), actual);
    }
        
    @Retention(RetentionPolicy.RUNTIME)
    @interface IgnoreThis {}
}

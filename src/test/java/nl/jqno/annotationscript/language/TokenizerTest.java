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
        @Zero(literal="if")@Zero(literal="1")@Zero(literal="'one'")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeRecursiveValidInput() {
        @Zero(literal="begin")
        @Zero(list={@One(literal="define"), @One(literal="r"), @One(literal="10")})
        @Zero(list={
            @One(literal="*"),
            @One(literal="pi"),
            @One(list={@Two(literal="*"), @Two(literal="r"), @Two(literal="r")})})
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        var expected = List.of("(", "begin", 
                "(", "define", "r", "10", ")",
                "(", "*", "pi", "(", "*", "r", "r", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void tokenizeDeepInput() {
        @Zero(list=@One(list=@Two(list=@Three(literal="😜"))))
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        var expected = List.of("(", "(", "(", "😜", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void throwOnUninitializedAnnotation() {
        @Zero(literal="non-empty")@Zero
        class Input {}

        var sut = new Tokenizer(Input.class);
        var e = assertThrows(TokenizeException.class, () -> sut.tokenize());
        assertEquals("annotation has no value", e.getMessage());
    }

    @Test
    public void ignoreOtherAnnotations() {
        @Zero(literal="if")@IgnoreThis@Zero(literal="1")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", ")"), actual);
    }
        
    @Retention(RetentionPolicy.RUNTIME)
    @interface IgnoreThis {}
}

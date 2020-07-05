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
        @Zero("if")@Zero("1")@Zero("'one'")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", "'one'", ")"), actual);
    }

    @Test
    public void tokenizeRecursiveValidInput() {
        @Zero("begin")
        @Zero(list={@One("define"), @One("r"), @One("10")})
        @Zero(list={
            @One("*"),
            @One("pi"),
            @One(list={@Two("*"), @Two("r"), @Two("r")})})
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
        @Zero(list=@One(list=@Two(list=@Three(list=@Four(list=@Five(list=@Six(list=
            @Seven(list=@Eight(list=@Nine(list=@Ten(list=@Eleven("😜")))))    
        )))))))
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        var expected = List.of("(", "(", "(", "(", "(", "(", "(",
                "(",  "(", "(", "(", "😜", ")", ")", ")", ")",
                ")", ")", ")", ")", ")", ")", ")");
        assertEquals(expected, actual);
    }

    @Test
    public void tokenizeIncludes() {
        @Zero("define")@Zero("r")@Zero("10")
        class InputDefine {}

        @Zero("*")@Zero("r")@Zero("r")
        class InputMultiply {}

        @Zero("begin")
        @Zero(include=InputDefine.class)
        @Zero(list={@One("*"), @One("pi"), @One(include=InputMultiply.class)})
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
        @Zero("non-empty")@Zero
        class Input {}

        var sut = new Tokenizer(Input.class);
        var e = assertThrows(TokenizeException.class, () -> sut.tokenize());
        assertEquals("annotation has no value", e.getMessage());
    }

    @Test
    public void ignoreOtherAnnotations() {
        @Zero("if")@IgnoreThis@Zero("1")
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", ")"), actual);
    }
        
    @Retention(RetentionPolicy.RUNTIME)
    @interface IgnoreThis {}
}

package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import nl.jqno.annotationscript.Annotations.*;

public class TokenizerTest {
    @Test
    public void tokenizeValidInput() {
        @Begin@Sym("if")@Int(1)@End
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", "if", "1", ")"), actual);
    }

    @Test
    public void ignoreOtherAnnotations() {
        @Begin@IgnoreThis@End
        class Input {}

        var sut = new Tokenizer(Input.class);
        var actual = sut.tokenize();
        assertEquals(List.of("(", ")"), actual);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface IgnoreThis {}
}

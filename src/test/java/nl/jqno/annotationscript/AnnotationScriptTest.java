package nl.jqno.annotationscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import nl.jqno.annotationscript.Annotations.*;

public class AnnotationScriptTest {

    @Test
    public void runCalculation() {
        @Zero("begin")
        @Zero(list={@One("define"), @One("r"), @One("10")})
        @Zero(list={
            @One("*"),
            @One("pi"),
            @One(list={@Two("*"), @Two("r"), @Two("r")})})
        class Input {}

        var actual = (double)AnnotationScript.run(Input.class);
        var expected = 314.159265;
        assertEquals(expected, actual, 0.000001);
    }

    @Test
    public void runCalculationWithInitialValue() {
        @Zero("*")
        @Zero("pi")
        @Zero(list={@One("*"), @One("r"), @One("r")})
        class Input {}

        var initialValues = HashMap.<String, Object>of("r", 10);
        var actual = (double)AnnotationScript.run(Input.class, initialValues);
        var expected = 314.159265;
        assertEquals(expected, actual, 0.000001);
    }

    @Test
    public void generateCalculation() {
        var input = """
            (begin
              (define r 10)
              (* pi (* r r)))
            """;
        var actual = AnnotationScript.generate(input);
        var expected = "@Zero(\"begin\")" +
            "@Zero(list={@One(\"define\"), @One(\"r\"), @One(\"10\")})" +
            "@Zero(list={" +
                "@One(\"*\"), " +
                "@One(\"pi\"), " +
                "@One(list={@Two(\"*\"), @Two(\"r\"), @Two(\"r\")})})";
        assertEquals(expected, actual);
    }

}

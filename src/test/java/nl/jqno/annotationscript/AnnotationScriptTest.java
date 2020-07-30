package nl.jqno.annotationscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
    public void generateCalculation() {
        var input = "(begin (define r 10) (* pi (* r r)))";
        var actual = AnnotationScript.generate(input);
        var expected = "@Zero(list={@One(\"begin\"), " +
            "@One(list={@Two(\"define\"), @Two(\"r\"), @Two(\"10\")}), " +
            "@One(list={" +
                "@Two(\"*\"), " +
                "@Two(\"pi\"), " +
                "@Two(list={@Three(\"*\"), @Three(\"r\"), @Three(\"r\")})})})";
        assertEquals(expected, actual);
    }

}

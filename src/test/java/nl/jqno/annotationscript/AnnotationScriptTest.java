package nl.jqno.annotationscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.Annotations.*;

public class AnnotationScriptTest {

    @Test
    public void calculation() {
        @Zero(literal="begin")
        @Zero(list={@One(literal="define"), @One(literal="r"), @One(literal="10")})
        @Zero(list={
            @One(literal="*"),
            @One(literal="pi"),
            @One(list={@Two(literal="*"), @Two(literal="r"), @Two(literal="r")})})
        class Input {}

        var actual = (double)AnnotationScript.run(Input.class);
        var expected = 314.159265;
        assertEquals(expected, actual, 0.000001);
    }

}

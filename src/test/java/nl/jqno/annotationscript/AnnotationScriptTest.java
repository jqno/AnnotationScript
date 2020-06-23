package nl.jqno.annotationscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.Annotations.*;

public class AnnotationScriptTest {

    @Test
    public void calculation() {
        @One(literal="begin")
        @One(list={@Two(literal="define"), @Two(literal="r"), @Two(literal="10")})
        @One(list={
            @Two(literal="*"),
            @Two(literal="pi"),
            @Two(list={@Three(literal="*"), @Three(literal="r"), @Three(literal="r")})})
        class Input {}

        var actual = (double)AnnotationScript.run(Input.class);
        var expected = 314.159265;
        assertEquals(expected, actual, 0.000001);
    }

}

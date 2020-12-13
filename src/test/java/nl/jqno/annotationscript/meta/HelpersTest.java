package nl.jqno.annotationscript.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

public class HelpersTest {

    private static final Map<String, Object> INPUT =
        HashMap.of("input", List.of(1, 2, 3, 4));

    @Nested
    class First {
        @Zero("begin")
        @Zero(include=Helpers.First.class)
        @Zero(list={@One("first"), @One("input")})
        class Sut {}

        @Test
        public void happy() {
            assertEquals(1, AnnotationScript.run(Sut.class, INPUT));
        }
    }

    @Nested
    class Second {
        @Zero("begin")
        @Zero(include=Helpers.Second.class)
        @Zero(list={@One("second"), @One("input")})
        class Sut {}

        @Test
        public void happy() {
            assertEquals(2, AnnotationScript.run(Sut.class, INPUT));
        }
    }

    @Nested
    class Third {
        @Zero("begin")
        @Zero(include=Helpers.Third.class)
        @Zero(list={@One("third"), @One("input")})
        class Sut {}

        @Test
        public void happy() {
            assertEquals(3, AnnotationScript.run(Sut.class, INPUT));
        }
    }
}

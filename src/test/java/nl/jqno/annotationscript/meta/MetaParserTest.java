package nl.jqno.annotationscript.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.One;
import nl.jqno.annotationscript.Annotations.Zero;
import nl.jqno.annotationscript.language.Symbol;

public class MetaParserTest  {

    @Nested
    class Second {
        @Zero("begin")
        @Zero(include=Parser.Second.class)
        @Zero(list={@One("second"), @One("input")})
        class Sut {}

        @Test
        public void happy() {
            assertEquals(2, run(Sut.class, List.of(1, 2, 3)));
        }
    }

    @Nested
    class ReadAtom {
        @Zero("begin")
        @Zero(include=Parser.ReadAtom.class)
        @Zero(list={@One("read-atom"), @One("input")})
        class Sut {}

        @Test
        public void readString() {
            assertEquals("string", run(Sut.class, "'string'"));
        }

        @Test
        public void readInt() {
            assertEquals(42, run(Sut.class, "42"));
        }

        @Test
        public void readFloat() {
            assertEquals(13.37, run(Sut.class, "13.37"));
        }

        @Test
        public void readSymbol() {
            assertEquals(new Symbol("some-symbol"), run(Sut.class, "some-symbol"));
            assertEquals(new Symbol("+"), run(Sut.class, "+"));
        }
    }

    private Object run(Class<?> source, Object input) {
        return AnnotationScript.run(source, HashMap.of("input", input));
    }
}

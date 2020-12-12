package nl.jqno.annotationscript.meta;

import static nl.jqno.annotationscript.AnnotationScript.run;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.Annotations.*;
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
            assertEquals(2, run(Sut.class, input(List.of(1, 2, 3))));
        }
    }

    @Nested
    class ReadList {
        @Zero("begin")
        @Zero(include=Parser.Second.class)
        @Zero(include=Parser.ReadList.class)
        @Zero(list={
            @One("define"),
            @One("read-from-tokens"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("tokens")}),
                @Two(list={
                    @Three("list"),
                    @Three(list={@Four("head"), @Four("tokens")}),
                    @Three(list={@Four("tail"), @Four("tokens")})})})})
        @Zero(list={@One("read-list"), @One("acc"), @One("initial-tokens")})
        class Sut {}

        @Test
        public void empty() {
            var initialValues = input(List.empty(), List.empty());
            var e = assertThrows(RuntimeException.class, () -> run(Sut.class, initialValues));
            assertEquals("unexpected EOF", e.getMessage());
        }

        @Test
        public void endOfList() {
            var acc = List.of(1, 2, 3);
            var initialValues = input(acc, List.of(")", "tail"));
            var actual = run(Sut.class, initialValues);
            var expected = List.of(acc, List.of("tail"));
            assertEquals(expected, actual);
        }

        @Test
        public void middleOfList() {
            var acc = List.of(1, 2, 3);
            var initialTokens = List.of(4, 5, ")", "tail");
            var actual = run(Sut.class, input(acc, initialTokens));
            var expected = List.of(List.of(1, 2, 3, 4, 5), List.of("tail"));
            assertEquals(expected, actual);
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
            assertEquals("string", run(Sut.class, input("'string'")));
        }

        @Test
        public void readInt() {
            assertEquals(42, run(Sut.class, input("42")));
        }

        @Test
        public void readFloat() {
            assertEquals(13.37, run(Sut.class, input("13.37")));
        }

        @Test
        public void readSymbol() {
            assertEquals(new Symbol("some-symbol"), run(Sut.class, input("some-symbol")));
            assertEquals(new Symbol("+"), run(Sut.class, input("+")));
        }
    }

    private Map<String, Object> input(Object input) {
        return HashMap.of("input", input);
    }

    private Map<String, Object> input(Object acc, Object initialTokens) {
        return HashMap.of("acc", acc, "initial-tokens", initialTokens);
    }
}

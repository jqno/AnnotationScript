package nl.jqno.annotationscript.meta;

import static nl.jqno.annotationscript.AnnotationScript.run;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.Annotations.*;
import nl.jqno.annotationscript.language.Symbol;

public class EvaluatorTest {

    @Zero("define")
    @Zero("help")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("txt")}),
        @One(list={
            @Two("lambda"),
            @Two(list={@Three("x")}),
            @Two(list={@Three("str/concat"), @Three("txt"), @Three("x")})})})
    class TestHelp {}

    @Nested
    class ExpressionToAction {
        @Zero("begin")
        @Zero(include=TestHelp.class)
        @Zero(list={
            @One("define"),
            @One("atom-to-action"),
            @One(list={@Two("help"), @Two("'atom-to-action '")})})
        @Zero(list={
            @One("define"),
            @One("list-to-action"),
            @One(list={@Two("help"), @Two("'list-to-action '")})})
        @Zero(include=Evaluator.ExpressionToAction.class)
        @Zero(list={@One("expression-to-action"), @One("e")})
        class Sut {}

        @Test
        public void atom() {
            var initialValues = input("e", 1);
            var actual = run(Sut.class, initialValues);
            assertEquals("atom-to-action 1", actual);
        }

        @Test
        public void list() {
            var initialValues = input("e", List.of(1));
            var actual = run(Sut.class, initialValues);
            assertEquals("list-to-action List(1)", actual);
        }
    }

    @Nested
    class AtomToAction {
        @Zero("begin")
        @Zero(include=Evaluator.AtomToAction.class)
        @Zero(list={@One("define"), @One("*const"), @One("'*const'")})
        @Zero(list={@One("define"), @One("*identifier"), @One("'*identifier'")})
        @Zero(list={@One("atom-to-action"), @One("e")})
        class Sut {}

        @Test
        public void number() {
            var initialValues = input("e", 42);
            var actual = run(Sut.class, initialValues);
            assertEquals("*const", actual);
        }

        @Test
        public void bool() {
            var initialValues = input("e", new Symbol("#t"));
            var actual = run(Sut.class, initialValues);
            assertEquals("*const", actual);
        }

        @Test
        public void function() {
            var initialValues = input("e", new Symbol("eq?"));
            var actual = run(Sut.class, initialValues);
            assertEquals("*const", actual);
        }

        @Test
        public void identifier() {
            var initialValues = input("e", new Symbol("something-else"));
            var actual = run(Sut.class, initialValues);
            assertEquals("*identifier", actual);
        }
    }

    @Nested
    class ListToAction {
        @Zero("begin")
        @Zero(include=Evaluator.ListToAction.class)
        @Zero(list={@One("define"), @One("*quote"), @One("'*quote'")})
        @Zero(list={@One("define"), @One("*lambda"), @One("'*lambda'")})
        @Zero(list={@One("define"), @One("*cond"), @One("'*cond'")})
        @Zero(list={@One("define"), @One("*application"), @One("'*application'")})
        @Zero(list={@One("list-to-action"), @One("e")})
        class Sut {}

        @Test
        public void quote() {
            var initialValues = input("e", List.of(new Symbol("quote"), 1));
            var actual = run(Sut.class, initialValues);
            assertEquals("*quote", actual);
        }

        @Test
        public void lambda() {
            var initialValues = input("e", List.of(new Symbol("lambda"), 1));
            var actual = run(Sut.class, initialValues);
            assertEquals("*lambda", actual);
        }

        @Test
        public void cond() {
            var initialValues = input("e", List.of(new Symbol("cond"), 1));
            var actual = run(Sut.class, initialValues);
            assertEquals("*cond", actual);
        }

        @Test
        public void otherAtom() {
            var initialValues = input("e", List.of(new Symbol("other-atom"), 1));
            var actual = run(Sut.class, initialValues);
            assertEquals("*application", actual);
        }

        @Test
        public void list() {
            var initialValues = input("e", List.of(List.of(1), 1));
            var actual = run(Sut.class, initialValues);
            assertEquals("*application", actual);
        }
    }

    @Nested
    class Value {
        @Zero("begin")
        @Zero(include=Evaluator.Value.class)
        @Zero(list={
            @One("define"),
            @One("meaning"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two(list={
                    @Three("str/concat"),
                    @Three("'meaning '"),
                    @Three("e"),
                    @Three("', '"),
                    @Three("table")})})})
        @Zero(list={@One("value"), @One("e")})
        class Sut {}

        @Test
        public void value() {
            var initialValues = input("e", 42);
            var actual = run(Sut.class, initialValues);
            assertEquals("meaning 42, List()", actual);
        }
    }

    private Map<String, Object> input(String key1, Object val1) {
        return HashMap.of(key1, val1);
    }
}
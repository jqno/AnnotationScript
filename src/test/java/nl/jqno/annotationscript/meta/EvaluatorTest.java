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

    @Nested
    class Meaning {
        @Zero("begin")
        @Zero(include=Evaluator.Meaning.class)
        @Zero(list={
            @One("define"),
            @One("expression-to-action"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two(list={
                    @Three("lambda"),
                    @Three(list={@Four("e"), @Four("table")}),
                    @Three(list={
                        @Four("str/concat"),
                        @Four("'e-to-a '"),
                        @Four("e"),
                        @Four("', '"),
                        @Four("table")})})})})
        @Zero(list={@One("meaning"), @One("e"), @One("table")})
        class Sut {}

        @Test
        public void meaning() {
            var initialValues = input("e", 42, "table", List.of(1337));
            var actual = run(Sut.class, initialValues);
            assertEquals("e-to-a 42, List(1337)", actual);
        }
    }

    @Nested
    class TypeConst {
        @Zero("begin")
        @Zero(include=Helpers.Build.class)
        @Zero(include=Evaluator.TypeConst.class)
        @Zero(list={@One("*const"), @One("e"), @One("table")})
        class Sut {}

        @Test
        public void number() {
            var initialValues = input("e", 42, "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(42, actual);
        }

        @Test
        public void t() {
            var initialValues = input("e", true, "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(true, actual);
        }

        @Test
        public void f() {
            var initialValues = input("e", false, "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(false, actual);
        }

        @Test
        public void primitive() {
            var initialValues = input("e", new Symbol("something"), "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(new Symbol("primitive"), new Symbol("something")), actual);
        }
    }

    @Nested
    class TypeQuote {
        @Zero("begin")
        @Zero(include=Helpers.Second.class)
        @Zero(include=Evaluator.TypeQuote.class)
        @Zero(include=Evaluator.TextOf.class)
        @Zero(list={@One("*quote"), @One("e"), @One("table")})
        class Sut {}

        @Test
        public void quote() {
            var initialValues = input(
                "e", List.of(new Symbol("quote"), new Symbol("something-quoted")),
                "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(new Symbol("something-quoted"), actual);
        }
    }

    @Nested
    class TypeIdentifier {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.class)
        @Zero(include=Evaluator.TypeIdentifier.class)
        @Zero(include=Evaluator.InitialTable.class)
        @Zero(list={@One("*identifier"), @One("e"), @One("table")})
        class Sut {}

        private final Object table = List.of(
            List.of(List.of("entree", "dessert"), List.of("spaghetti", "spumoni")),
            List.of(List.of("appetizer", "entree", "beverage"), List.of("food", "tastes", "good")));

        @Test
        public void identifier() {
            var initialValues = input("e", "entree", "table", table);
            var actual = run(Sut.class, initialValues);
            assertEquals("spaghetti", actual);
        }
    }

    @Nested
    class TypeLambda {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.TypeLambda.class)
        @Zero(list={@One("*lambda"), @One("e"), @One("table")})
        class Sut {}

        private final List<?> lambda = List.of(new Symbol("lambda"), List.of(new Symbol("e")), List.of("e"));
        private final Object table = List.of(1, 2, 3);

        @Test
        public void lambda() {
            var initialValues = input("e", lambda, "table", table);
            var actual = run(Sut.class, initialValues);
            var expected = List.of(new Symbol("non-primitive"), List.of(table, lambda.get(1), lambda.get(2)));
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Evcon {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.Evcon.class)
        @Zero(include=Evaluator.Else.class)
        @Zero(include=Evaluator.QuestionOf.class)
        @Zero(include=Evaluator.AnswerOf.class)
        @Zero(list={
            @One("define"),
            @One("meaning"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two("e")})})
        @Zero(list={@One("evcon"), @One("lines"), @One("table")})
        class Sut {}

        @Test
        public void firstCase() {
            var lines = List.of(
                List.of(new Symbol("#t"), 1),
                List.of(new Symbol("#f"), 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(actual, 1);
        }

        @Test
        public void middleCase() {
            var lines = List.of(
                List.of(new Symbol("#f"), 1),
                List.of(new Symbol("#t"), 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(actual, 1);
        }
        
        @Test
        public void elseCase() {
            var lines = List.of(
                List.of(new Symbol("#f"), 1),
                List.of(new Symbol("#f"), 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(actual, 1);
        }
    }

    @Nested
    class TypeCond {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.TypeCond.class)
        @Zero(include=Evaluator.CondLinesOf.class)
        @Zero(list={
            @One("define"),
            @One("evcon"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("lines"), @Three("table")}),
                @Two(list={@Three("str/concat"), @Three("lines"), @Three("table")})})})
        @Zero(list={@One("*cond"), @One("e"), @One("table")})
        class Sut {}

        private final Object table = List.of(1, 2, 3);

        @Test
        public void cond() {
            var initialValues = input("e", List.of(new Symbol("cond"), 1, 2, 3, 4), "table", table);
            var actual = run(Sut.class, initialValues);
            assertEquals(actual, "List(1, 2, 3, 4)List(1, 2, 3)");
        }
    }

    @Nested
    class Evlis {
        @Zero("begin")
        @Zero(include=Evaluator.Evlis.class)
        @Zero(list={
            @One("define"),
            @One("meaning"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two("e")})})
        @Zero(list={@One("evlis"), @One("args"), @One("table")})
        class Sut {}

        @Test
        public void empty() {
            var initialValues = input("args", List.empty(), "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(List.empty(), actual);
        }

        @Test
        public void list() {
            var initialValues = input("args", List.of(1, 2, 3, 4), "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(1, 2, 3, 4), actual);
        }
    }

    private Map<String, Object> input(String key1, Object val1) {
        return HashMap.of(key1, val1);
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2) {
        return HashMap.of(key1, val1, key2, val2);
    }
}

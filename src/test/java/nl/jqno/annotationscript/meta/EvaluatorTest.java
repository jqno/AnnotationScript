package nl.jqno.annotationscript.meta;

import static nl.jqno.annotationscript.language.Symbol.FALSE;
import static nl.jqno.annotationscript.language.Symbol.TRUE;
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
        @Zero(list={@One("define"), @One("*define"), @One("'*define'")})
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
            var initialValues = input("e", TRUE, "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void f() {
            var initialValues = input("e", FALSE, "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
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
                List.of(TRUE, 1),
                List.of(FALSE, 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(1, actual);
        }

        @Test
        public void middleCase() {
            var lines = List.of(
                List.of(FALSE, 1),
                List.of(TRUE, 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(2, actual);
        }
        
        @Test
        public void elseCase() {
            var lines = List.of(
                List.of(FALSE, 1),
                List.of(FALSE, 2),
                List.of(new Symbol("else"), 3));
            var initialValues = input("lines", lines, "table", null);
            var actual = run(Sut.class, initialValues);
            assertEquals(3, actual);
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
            assertEquals("List(1, 2, 3, 4)List(1, 2, 3)", actual);
        }
    }

    @Nested
    class TypeDefine {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.class)
        @Zero(include=Evaluator.TypeDefine.class)
        @Zero(list={
            @One("define"),
            @One("meaning"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two(list={@Three("str/concat"), @Three("e"), @Three("' - '"), @Three("table")})})})
        @Zero(list={@One("*define"), @One("e"), @One("table")})
        class Sut {}

        @Test
        public void define() {
            var initialValues = input(
                "e", List.of("define", List.of("r", 10), List.of(new Symbol("r"))),
                "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals("List('r) - List(List(List(r), List(10 - List())))", actual);
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

    @Nested
    class TypeApplication {
        @Zero("begin")
        @Zero(include=Evaluator.TypeApplication.class)
        @Zero(include=Evaluator.FunctionOf.class)
        @Zero(include=Evaluator.ArgumentsOf.class)
        @Zero(include=Evaluator.Evlis.class)
        @Zero(list={
            @One("define"),
            @One("meaning"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("e"), @Three("table")}),
                @Two("e")})})
        @Zero(list={
            @One("define"),
            @One(":apply"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("x"), @Three("xs")}),
                @Two(list={
                    @Three("str/concat"),
                    @Three("x"),
                    @Three("xs")})})})
        @Zero(list={@One("*application"), @One("e"), @One("table")})
        class Sut {}

        @Test
        public void application() {
            var initialValues = input("e", List.of(1, 2, 3, 4), "table", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals("1List(2, 3, 4)", actual);
        }
    }

    @Nested
    class Primitive {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.Primitive.class)
        @Zero(list={@One("primitive?"), @One("l")})
        class Sut {}

        @Test
        public void primitive() {
            var initialValues = input("l", List.of(new Symbol("primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void nonPrimitive() {
            var initialValues = input("l", List.of(new Symbol("non-primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }
    }

    @Nested
    class NonPrimitive {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.NonPrimitive.class)
        @Zero(list={@One("non-primitive?"), @One("l")})
        class Sut {}

        @Test
        public void nonPrimitive() {
            var initialValues = input("l", List.of(new Symbol("non-primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void primitive() {
            var initialValues = input("l", List.of(new Symbol("primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }
    }

    @Nested
    class Apply {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.Primitive.class)
        @Zero(include=Evaluator.NonPrimitive.class)
        @Zero(include=Evaluator.Apply.class)
        @Zero(list={
            @One("define"),
            @One("apply-primitive"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("name"), @Three("vals")}),
                @Two(list={
                    @Three("str/concat"),
                    @Three("'primitive: '"),
                    @Three("name"),
                    @Three("', '"),
                    @Three("vals")})})})
        @Zero(list={
            @One("define"),
            @One("apply-closure"),
            @One(list={
                @Two("lambda"),
                @Two(list={@Three("name"), @Three("vals")}),
                @Two(list={
                    @Three("str/concat"),
                    @Three("'closure: '"),
                    @Three("name"),
                    @Three("', '"),
                    @Three("vals")})})})
        @Zero(list={@One(":apply"), @One("name"), @One("vals")})
        class Sut {}

        @Test
        public void primitive() {
            var initialValues = input(
                "name", List.of(new Symbol("primitive"), new Symbol("some-name")),
                "vals", List.of(1, 2, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals("primitive: 'some-name, List(1, 2, 3)", actual);
        }

        @Test
        public void closure() {
            var initialValues = input(
                "name", List.of(new Symbol("non-primitive"), new Symbol("some-name")),
                "vals", List.of(1, 2, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals("closure: 'some-name, List(1, 2, 3)", actual);
        }
    }

    @Nested
    class ApplyPrimitive {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Evaluator.ApplyPrimitive.class)
        @Zero(list={@One("define"), @One(":atom?"), @One("atom?")})
        @Zero(list={@One("apply-primitive"), @One("name"), @One("vals")})
        class Sut {}

        @Test
        public void cons() {
            var initialValues = input(
                "name", new Symbol("cons"),
                "vals", List.of(1, List.of(2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(1, 2, 3), actual);
        }

        @Test
        public void car() {
            var initialValues = input(
                "name", new Symbol("car"),
                "vals", List.of(List.of(1, 2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(1, actual);
        }

        @Test
        public void cdr() {
            var initialValues = input(
                "name", new Symbol("cdr"),
                "vals", List.of(List.of(1, 2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(2, 3), actual);
        }

        @Test
        public void nullp() {
            var initialValues = input(
                "name", new Symbol("null?"),
                "vals", List.of(List.empty()));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void eqp() {
            var initialValues = input(
                "name", new Symbol("eq?"),
                "vals", List.of(1, 2));
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }

        @Test
        public void atomp() {
            var initialValues = input(
                "name", new Symbol("atom?"),
                "vals", List.of(1));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void zerop() {
            var initialValues = input(
                "name", new Symbol("zero?"),
                "vals", List.of(1));
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }

        @Test
        public void add1() {
            var initialValues = input(
                "name", new Symbol("add1"),
                "vals", List.of(2));
            var actual = run(Sut.class, initialValues);
            assertEquals(3, actual);
        }

        @Test
        public void sub1() {
            var initialValues = input(
                "name", new Symbol("sub1"),
                "vals", List.of(2));
            var actual = run(Sut.class, initialValues);
            assertEquals(1, actual);
        }

        @Test
        public void modulo() {
            var initialValues = input(
                "name", new Symbol("mod"),
                "vals", List.of(5, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(2, actual);
        }

        @Test
        public void plus() {
            var initialValues = input(
                "name", new Symbol("+"),
                "vals", List.of(5, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(8, actual);
        }

        @Test
        public void minus() {
            var initialValues = input(
                "name", new Symbol("-"),
                "vals", List.of(5, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(2, actual);
        }

        @Test
        public void lessThan() {
            var initialValues = input(
                "name", new Symbol("<"),
                "vals", List.of(5, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(new Symbol("#f"), actual);
        }

        @Test
        public void greaterThan() {
            var initialValues = input(
                "name", new Symbol(">"),
                "vals", List.of(5, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(new Symbol("#t"), actual);
        }

        @Test
        public void nth() {
            var initialValues = input(
                "name", new Symbol("nth!"),
                "vals", List.of(2, List.of(0, 1, 2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(2, actual);
        }

        @Test
        public void updateNth() {
            var initialValues = input(
                "name", new Symbol("update-nth!"),
                "vals", List.of(2, 42, List.of(0, 1, 2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(0, 1, 42, 3), actual);
        }

        @Test
        public void length() {
            var initialValues = input(
                "name", new Symbol("length!"),
                "vals", List.of(List.of(0, 1, 2, 3)));
            var actual = run(Sut.class, initialValues);
            assertEquals(4, actual);
        }

        @Test
        public void numberp() {
            var initialValues = input(
                "name", new Symbol("number?"),
                "vals", List.of(1));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }
    }

    @Nested
    class Atom {
        @Zero("begin")
        @Zero(include=Evaluator.Atom.class)
        @Zero(list={@One(":atom?"), @One("x")})
        class Sut {}

        @Test
        public void atom() {
            var initialValues = input("x", 1);
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void empty() {
            var initialValues = input("x", List.empty());
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }

        @Test
        public void primitive() {
            var initialValues = input("x", List.of(new Symbol("primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void nonPrimitive() {
            var initialValues = input("x", List.of(new Symbol("non-primitive"), new Symbol("something")));
            var actual = run(Sut.class, initialValues);
            assertEquals(TRUE, actual);
        }

        @Test
        public void list() {
            var initialValues = input("x", List.of(1, 2, 3));
            var actual = run(Sut.class, initialValues);
            assertEquals(FALSE, actual);
        }
    }

    @Nested
    class ApplyClosure {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.class)
        @Zero(include=Evaluator.BodyOf.class)
        @Zero(include=Evaluator.FormalsOf.class)
        @Zero(include=Evaluator.TableOf.class)
        @Zero(include=Evaluator.ApplyClosure.class)
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
        @Zero(list={@One("apply-closure"), @One("closure"), @One("vals")})
        class Sut {}

        @Test
        public void closure() {
            var initialValues = input(
                "closure", List.of(List.of("table"), List.of("a", "b"), "body"),
                "vals", List.of(1, 2));
            var actual = run(Sut.class, initialValues);
            assertEquals("meaning body, List(List(List(a, b), List(1, 2)), table)", actual);
        }
    }

    @Nested
    class Integration {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.class)
        @Zero(include=Evaluator.class)
        @Zero(list={@One("value"), @One("e")})
        class Sut {}

        @Test
        public void addition() {
            var initialValues = input("e", List.of(new Symbol("add1"), 42));
            var actual = run(Sut.class, initialValues);
            assertEquals(43, actual);
        }

        @Test
        public void quote() {
            var initialValues = input("e", List.of(new Symbol("quote"), List.of(new Symbol("a"), new Symbol("b"))));
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(new Symbol("a"), new Symbol("b")), actual);
        }

        @Test
        public void car() {
            var initialValues = input("e", List.of(new Symbol("car"), List.of(new Symbol("quote"), List.of(1, 2, 3))));
            var actual = run(Sut.class, initialValues);
            assertEquals(1, actual);
        }

        @Test
        public void lists() {
            var initialValues = input("e", List.of(
                new Symbol("cons"),
                List.of(new Symbol("car"), List.of(new Symbol("quote"), List.of(1, 2, 3))),
                List.of(new Symbol("cdr"), List.of(new Symbol("quote"), List.of(new Symbol("a"), new Symbol("b"), new Symbol("c"))))));
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(1, new Symbol("b"), new Symbol("c")), actual);
        }

        @Test
        public void lambdaWithBooleans() {
            var initialValues = input("e", List.of(
                List.of(
                    new Symbol("lambda"),
                    List.of(new Symbol("x")),
                    List.of(new Symbol("cond"),
                        List.of(new Symbol("x"), List.of(new Symbol("quote"), new Symbol("true"))),
                        List.of(new Symbol("else"), List.of(new Symbol("quote"), new Symbol("false"))))),
                Symbol.TRUE));
            var actual = run(Sut.class, initialValues);
            assertEquals(new Symbol("true"), actual);
        }
    }

    private Map<String, Object> input(String key1, Object val1) {
        return HashMap.of(key1, val1);
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2) {
        return HashMap.of(key1, val1, key2, val2);
    }
}

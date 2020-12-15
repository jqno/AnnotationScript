package nl.jqno.annotationscript.meta;

import static nl.jqno.annotationscript.AnnotationScript.run;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.Annotations.*;

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

    private Map<String, Object> input(String key1, Object val1) {
        return HashMap.of(key1, val1);
    }
}

package nl.jqno.annotationscript.language;

import java.lang.annotation.Annotation;

import io.vavr.collection.List;
import nl.jqno.annotationscript.Annotations;
import nl.jqno.annotationscript.Annotations.*;
import nl.jqno.annotationscript.language.exceptions.TokenizeException;

public class Tokenizer {
    private final Class<?> source;

    public Tokenizer(Class<?> source) {
        this.source = source;
    }

    public List<String> tokenize() {
        return getAnnotations()
            .filter(this::isEligible)
            .flatMap(this::tokenizeAnnotation);
    }

    private List<Annotation> getAnnotations() {
        return List.of(source.getAnnotations());
    }

    private boolean isEligible(Annotation annotation) {
        return annotation.annotationType().getName().startsWith(Annotations.class.getName());
    }

    private List<String> tokenizeAnnotation(Annotation a) {
        if (a instanceof Zero) {
            Zero ann = (Zero)a;
            return tokenizeSingleAnnotation(ann.value(), ann.list());
        }
        if (a instanceof One) {
            One ann = (One)a;
            return tokenizeSingleAnnotation(ann.value(), ann.list());
        }
        if (a instanceof Two) {
            Two ann = (Two)a;
            return tokenizeSingleAnnotation(ann.value(), ann.list());
        }
        if (a instanceof Three) {
            Three ann = (Three)a;
            return tokenizeSingleAnnotation(ann.value(), new Annotation[] {});
        }
        return tokenizeListOfAnnotations(((ProgramHolder)a).value());
    }

    private List<String> tokenizeListOfAnnotations(Annotation[] list) {
        var tokenized = List.of(list).flatMap(this::tokenizeAnnotation);
        return List.of("(").appendAll(tokenized).append(")");
    }

    private List<String> tokenizeSingleAnnotation(String value, Annotation[] list) {
        if (!Annotations.EMPTY.equals(value)) {
            return List.of(value);
        }
        if (list.length > 0) {
            return tokenizeListOfAnnotations(list);
        }
        throw new TokenizeException("annotation has no value");
    }
}

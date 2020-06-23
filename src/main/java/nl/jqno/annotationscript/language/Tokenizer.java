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
        if (a instanceof One) {
            One one = (One)a;
            return tokenizeSingleAnnotation(one.literal(), one.list());
        }
        if (a instanceof Two) {
            Two two = (Two)a;
            return tokenizeSingleAnnotation(two.literal(), two.list());
        }
        if (a instanceof Three) {
            Three three = (Three)a;
            return tokenizeSingleAnnotation(three.literal(), new Annotation[] {});
        }
        return tokenizeListOfAnnotations(((ProgramHolder)a).value());
    }

    private List<String> tokenizeListOfAnnotations(Annotation[] list) {
        var tokenized = List.of(list).flatMap(this::tokenizeAnnotation);
        return List.of("(").appendAll(tokenized).append(")");
    }

    private List<String> tokenizeSingleAnnotation(String literal, Annotation[] list) {
        if (!Annotations.EMPTY.equals(literal)) {
            return List.of(literal);
        }
        if (list.length > 0) {
            return tokenizeListOfAnnotations(list);
        }
        throw new TokenizeException("annotation has no value");
    }
}

package nl.jqno.annotationscript.language;

import java.lang.annotation.Annotation;

import io.vavr.collection.List;
import nl.jqno.annotationscript.Annotations;
import nl.jqno.annotationscript.Annotations.*;
import nl.jqno.annotationscript.language.exceptions.TokenizeException;

public class AnnotationTokenizer implements Tokenizer {
    private static final Class<?>[] NONE = {};
    private final Class<?> source;

    public AnnotationTokenizer(Class<?> source) {
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

    // CHECKSTYLE OFF: CyclomaticComplexity
    // CHECKSTYLE OFF: NPathComplexity
    private List<String> tokenizeAnnotation(Annotation a) {
        if (a instanceof Zero ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), ann.export(), ann.list());
        }
        if (a instanceof One ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Two ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Three ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Four ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Five ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Six ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Seven ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Eight ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Nine ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Ten ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Eleven ann) {
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, new Annotation[] {});
        }
        return tokenizeListOfAnnotations(((ProgramHolder)a).value());
    }
    // CHECKSTYLE ON: NPathComplexity
    // CHECKSTYLE ON: CyclomaticComplexity

    private List<String> tokenizeListOfAnnotations(Annotation[] list) {
        var tokenized = List.of(list).flatMap(this::tokenizeAnnotation);
        return List.of("(").appendAll(tokenized).append(")");
    }

    private List<String> tokenizeSingleAnnotation(String value, Class<?> include, Class<?>[] export, Annotation[] list) {
        if (!Annotations.EMPTY.equals(value)) {
            return List.of(value);
        }
        if (!Annotations.Nothing.class.equals(include)) {
            return new AnnotationTokenizer(include).tokenize();
        }
        if (export.length > 0) {
            return List.of(export).flatMap(c -> new AnnotationTokenizer(c).tokenize());
        }
        if (list.length > 0) {
            return tokenizeListOfAnnotations(list);
        }
        throw new TokenizeException("annotation has no value");
    }
}

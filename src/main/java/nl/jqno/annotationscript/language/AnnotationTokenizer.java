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
    // CHECKSTYLE OFF: ExecutableStatementCount
    // CHECKSTYLE OFF: NPathComplexity
    private List<String> tokenizeAnnotation(Annotation a) {
        if (a instanceof Zero) {
            Zero ann = (Zero)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), ann.export(), ann.list());
        }
        if (a instanceof One) {
            One ann = (One)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Two) {
            Two ann = (Two)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Three) {
            Three ann = (Three)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Four) {
            Four ann = (Four)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Five) {
            Five ann = (Five)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Six) {
            Six ann = (Six)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Seven) {
            Seven ann = (Seven)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Eight) {
            Eight ann = (Eight)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Nine) {
            Nine ann = (Nine)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Ten) {
            Ten ann = (Ten)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, ann.list());
        }
        if (a instanceof Eleven) {
            Eleven ann = (Eleven)a;
            return tokenizeSingleAnnotation(ann.value(), ann.include(), NONE, new Annotation[] {});
        }
        return tokenizeListOfAnnotations(((ProgramHolder)a).value());
    }
    // CHECKSTYLE OFF: NPathComplexity
    // CHECKSTYLE OFF: ExecutableStatementCount
    // CHECKSTYLE OFF: CyclomaticComplexity

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

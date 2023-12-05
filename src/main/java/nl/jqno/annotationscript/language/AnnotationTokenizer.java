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
    private List<String> tokenizeAnnotation(Annotation annotation) {
        return switch (annotation) {
            case Zero a   -> tokenizeSingleAnnotation(a.value(), a.include(), a.export(), a.list());
            case One a    -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Two a    -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Three a  -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Four a   -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Five a   -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Six a    -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Seven a  -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Eight a  -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Nine a   -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Ten a    -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, a.list());
            case Eleven a -> tokenizeSingleAnnotation(a.value(), a.include(), NONE, new Annotation[] {});
            default       -> tokenizeListOfAnnotations(((ProgramHolder) annotation).value());
        };
    }
    //
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

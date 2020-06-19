package nl.jqno.annotationscript.language;

import java.lang.annotation.Annotation;
import io.vavr.collection.List;
import nl.jqno.annotationscript.Annotations;
import nl.jqno.annotationscript.Annotations.Begin;
import nl.jqno.annotationscript.Annotations.End;
import nl.jqno.annotationscript.Annotations.Int;
import nl.jqno.annotationscript.Annotations.Sym;

public class Tokenizer {
    private final Class<?> source;

    public Tokenizer(Class<?> source) {
        this.source = source;
    }

    public List<String> tokenize() {
        return getAnnotations()
            .filter(this::isEligible)
            .map(this::tokenizeSingleAnnotation);
    }

    private List<Annotation> getAnnotations() {
        return List.of(source.getAnnotations());
    }

    private boolean isEligible(Annotation annotation) {
        return annotation.annotationType().getName().startsWith(Annotations.class.getName());
    }

    private String tokenizeSingleAnnotation(Annotation a) {
        if (a instanceof Begin) {
            return "(";
        }
        else if (a instanceof End) {
            return ")";
        }
        else if (a instanceof Int) {
            return "" + ((Int)a).value();
        }
        else if (a instanceof Sym) {
            return ((Sym)a).value();
        }
        else {
            throw new IllegalStateException("Unsuported annotation: " + a.getClass().getName());
        }
    }
}

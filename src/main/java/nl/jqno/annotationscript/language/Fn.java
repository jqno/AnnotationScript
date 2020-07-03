package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;

public final class Fn {
    private final Function<List<Object>, Object> fn;

    public Fn(Function<List<Object>, Object> fn) {
        this.fn = fn;
    }

    public Object evaluate(List<Object> parameters) {
        return fn.apply(parameters);
    }
}

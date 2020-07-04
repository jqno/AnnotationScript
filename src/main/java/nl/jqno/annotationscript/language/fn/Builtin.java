package nl.jqno.annotationscript.language.fn;

import java.util.function.Function;

import io.vavr.collection.List;

public final class Builtin implements Fn {
    private final Function<List<Object>, Object> fn;

    public Builtin(Function<List<Object>, Object> fn) {
        this.fn = fn;
    }

    @Override
    public Object evaluate(List<Object> parameters) {
        return fn.apply(parameters);
    }
}

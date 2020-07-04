package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;

public final class Proc implements Fn {
    private final Function<List<Object>, Object> fn;

    public Proc(Function<List<Object>, Object> fn) {
        this.fn = fn;
    }

    @Override
    public Object evaluate(List<Object> parameters) {
        return fn.apply(parameters);
    }
}

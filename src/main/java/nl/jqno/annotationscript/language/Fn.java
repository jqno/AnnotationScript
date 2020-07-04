package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;

public interface Fn {

    public default Object value() {
        throw new IllegalStateException("This Fn is not a value");
    }

    public default Object evaluate(List<Object> parameters) {
        throw new IllegalStateException("This Fn is not a proc");
    }

    public static Fn proc(Function<List<Object>, Object> fn) {
        return new Proc(fn);
    }

    public static Fn value(Object value) {
        return new Value(value);
    }
}


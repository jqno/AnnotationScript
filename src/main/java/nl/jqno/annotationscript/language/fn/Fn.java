package nl.jqno.annotationscript.language.fn;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;

public interface Fn {

    public default Object value() {
        throw new IllegalStateException("This Fn is not a value");
    }

    public default Object evaluate(List<Object> parameters, Environment currentEnv, Evaluator evaluator) {
        throw new IllegalStateException("This Fn is not a builtin or lambda");
    }

    public static Fn builtin(Function<List<Object>, Object> fn) {
        return new Builtin(fn);
    }

    public static Fn val(Object value) {
        return new Value(value);
    }
}


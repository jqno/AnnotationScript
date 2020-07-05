package nl.jqno.annotationscript.language.fn;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;

public final class Builtin implements Fn {
    private final Function<List<Object>, Object> fn;

    Builtin(Function<List<Object>, Object> fn) {
        this.fn = fn;
    }

    @Override
    public Object evaluate(List<Object> parameters, Environment currentEnv, Evaluator evaluator) {
        return fn.apply(parameters);
    }
}

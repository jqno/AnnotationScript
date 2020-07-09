package nl.jqno.annotationscript.language.fn;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;

public final class Builtin implements Fn {
    private final Function3<List<Object>, Environment, Evaluator, Object> fn;

    Builtin(Function1<List<Object>, Object> fn) {
        this.fn = (params, env, eval) -> fn.apply(params);
    }

    Builtin(Function3<List<Object>, Environment, Evaluator, Object> fn) {
        this.fn = fn;
    }

    @Override
    public boolean isProcedure() {
        return true;
    }

    @Override
    public Object evaluate(List<Object> parameters, Environment currentEnv, Evaluator evaluator) {
        return fn.apply(parameters, currentEnv, evaluator);
    }
}

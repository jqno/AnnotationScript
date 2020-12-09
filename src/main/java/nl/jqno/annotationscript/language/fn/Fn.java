package nl.jqno.annotationscript.language.fn;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.Symbol;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public interface Fn {

    public boolean isProcedure();

    public default Object value() {
        throw new EvaluationException("This Fn is not a value");
    }

    public default Object evaluate(List<Object> parameters, Environment currentEnv, Evaluator evaluator) {
        throw new EvaluationException("This Fn is not a builtin or lambda");
    }

    public static Fn val(String name, Object value) {
        return new Value(name, value);
    }

    public static Fn builtin(String name, Function1<List<Object>, Object> fn) {
        return new Builtin(name, fn);
    }

    public static Fn builtin(String name, Function3<List<Object>, Environment, Evaluator, Object> fn) {
        return new Builtin(name, fn);
    }

    public static Fn lambda(List<Symbol> params, Object body, Environment capturedEnv) {
        return new Lambda(params, body, capturedEnv);
    }
}


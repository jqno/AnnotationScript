package nl.jqno.annotationscript.language.fn;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstExp;

public interface Fn {

    public default Object value() {
        throw new IllegalStateException("This Fn is not a value");
    }

    public default Object evaluate(List<Object> parameters) {
        throw new IllegalStateException("This Fn is not a builtin");
    }

    public static Fn builtin(Function<List<Object>, Object> fn) {
        return new Builtin(fn);
    }

    public static Fn value(Object value) {
        return new Value(value);
    }

    public static Fn exp(AstExp exp) {
        return new Exp(exp);
    }
}


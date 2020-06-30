package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstExp;

public final class Fn {
    private final Function<List<AstExp>, AstExp> fn;
    private final boolean isProc;

    private Fn(Function<List<AstExp>, AstExp> fn, boolean isProc) {
        this.fn = fn;
        this.isProc = isProc;
    }

    public static Fn value(AstExp value) {
        return new Fn(empty -> value, false);
    }

    public static Fn proc(Function<List<AstExp>, AstExp> fn) {
        return new Fn(fn, true);
    }

    public AstExp evaluate(List<AstExp> parameters) {
        return fn.apply(parameters);
    }

    public boolean isProc() {
        return isProc;
    }
}

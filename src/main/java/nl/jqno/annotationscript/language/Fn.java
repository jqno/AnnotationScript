package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstAtom;

public final class Fn {
    private final Function<List<AstAtom>, AstAtom> fn;
    private final boolean isProc;

    private Fn(Function<List<AstAtom>, AstAtom> fn, boolean isProc) {
        this.fn = fn;
        this.isProc = isProc;
    }

    public static Fn value(AstAtom value) {
        return new Fn(empty -> value, false);
    }

    public static Fn proc(Function<List<AstAtom>, AstAtom> fn) {
        return new Fn(fn, true);
    }

    public AstAtom evaluate(List<AstAtom> parameters) {
        return fn.apply(parameters);
    }

    public boolean isProc() {
        return isProc;
    }
}

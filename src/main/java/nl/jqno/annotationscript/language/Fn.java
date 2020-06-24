package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstAtom;

public class Fn {
    private final Function<List<AstAtom>, AstAtom> fn;

    public Fn(Function<List<AstAtom>, AstAtom> fn) {
        this.fn = fn;
    }

    public AstAtom evaluate(List<AstAtom> parameters) {
        return fn.apply(parameters);
    }
}

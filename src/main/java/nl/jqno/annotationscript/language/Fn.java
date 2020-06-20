package nl.jqno.annotationscript.language;

import java.util.function.Function;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstAtom;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class Fn {
    private final String name;
    private final int arity;
    private final Function<List<AstAtom>, AstAtom> fn;

    public Fn(String name, int arity, Function<List<AstAtom>, AstAtom> fn) {
        this.name = name;
        this.arity = arity;
        this.fn = fn;
    }

    public AstAtom evaluate(List<AstAtom> parameters) {
        if (parameters.size() != arity) {
            throw new EvaluationException("invalid number of parameters to " + name);
        }
        return fn.apply(parameters);
    }
}

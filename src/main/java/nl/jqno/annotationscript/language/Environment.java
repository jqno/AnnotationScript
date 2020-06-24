package nl.jqno.annotationscript.language;

import io.vavr.collection.Map;
import nl.jqno.annotationscript.language.ast.AstAtom;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public class Environment {
    private final Map<String, Fn> env;

    public Environment(Map<String, Fn> env) {
        this.env = env;
    }

    public Fn lookup(String symbol) {
        return env
            .get(symbol)
            .getOrElseThrow(() -> new EvaluationException("unknown symbol: " + symbol));
    }

    public Environment add(String symbol, AstAtom atom) {
        return new Environment(env.put(symbol, new Fn(params -> atom)));
    }
}

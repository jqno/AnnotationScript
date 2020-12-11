package nl.jqno.annotationscript.language;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.annotationscript.language.fn.Fn;

public class Environment {
    private final Map<Symbol, Fn> env;

    public Environment(Map<Symbol, Fn> env) {
        this.env = env;
    }

    public Option<Fn> lookupOption(Symbol symbol) {
        return env.get(symbol);
    }

    public Fn lookup(Symbol symbol) {
        return lookupOption(symbol).getOrElseThrow(() -> new EvaluationException("unknown symbol: " + symbol));
    }

    public Environment add(Symbol symbol, Fn fn) {
        return new Environment(env.put(symbol, fn));
    }

    public Environment merge(Environment other) {
        return new Environment(env.merge(other.env));
    }
}

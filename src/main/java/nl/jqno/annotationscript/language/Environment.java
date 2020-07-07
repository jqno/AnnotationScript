package nl.jqno.annotationscript.language;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.annotationscript.language.fn.Fn;

public class Environment {
    private final Map<String, Fn> env;

    public Environment(Map<String, Fn> env) {
        this.env = env;
    }

    public Option<Fn> lookupOption(String symbol) {
        return env.get(symbol);
    }

    public Fn lookup(String symbol) {
        return lookupOption(symbol).getOrElseThrow(() -> new EvaluationException("unknown symbol: " + symbol));
    }

    public Environment add(String symbol, Fn fn) {
        return new Environment(env.put(symbol, fn));
    }

    public Environment merge(Environment other) {
        return new Environment(env.merge(other.env));
    }
}

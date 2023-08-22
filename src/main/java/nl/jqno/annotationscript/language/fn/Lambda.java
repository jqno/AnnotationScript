package nl.jqno.annotationscript.language.fn;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.Symbol;

public final class Lambda implements Fn {
    private final List<Symbol> params;
    private final Object body;
    private final Environment capturedEnv;

    Lambda(List<Symbol> params, Object body, Environment capturedEnv) {
        this.params = params;
        this.body = body;
        this.capturedEnv = capturedEnv;
    }

    @Override
    public boolean isProcedure() {
        return true;
    }

    @Override
    public Object evaluate(List<Object> arguments, Environment currentEnv, Evaluator evaluator) {
        var combinedEnv = combine(arguments, currentEnv);
        return evaluator.eval(body, combinedEnv);
    }

    public Object getBody() {
        return body;
    }

    public Environment combine(List<Object> arguments, Environment currentEnv) {
        var zero = currentEnv.merge(capturedEnv);
        return params.zip(arguments)
            .foldLeft(zero, (acc, curr) -> acc.add(curr._1, Fn.val(curr._1.name(), curr._2)));
    }

    @Override
    public String toString() {
        return "λ[(" + params.mkString(",") + ") → " + body + "]";
    }
}

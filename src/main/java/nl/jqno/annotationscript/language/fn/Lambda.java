package nl.jqno.annotationscript.language.fn;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.ast.AstExp;
import nl.jqno.annotationscript.language.ast.AstSymbol;

public final class Lambda implements Fn {
    private final List<AstSymbol> params;
    private final AstExp body;
    private final Environment capturedEnv;

    Lambda(List<AstSymbol> params, AstExp body, Environment capturedEnv) {
        this.params = params;
        this.body = body;
        this.capturedEnv = capturedEnv;
    }

    @Override
    public Object evaluate(List<Object> arguments, Environment currentEnv, Evaluator evaluator) {
        var zero = currentEnv.merge(capturedEnv);
        var combinedEnv = params.zip(arguments)
            .foldLeft(zero, (acc, curr) -> acc.add(curr._1.asSymbol(), Fn.val(curr._2)));
        return evaluator.eval(body, combinedEnv);
    }
}

package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class Evaluator {
    public AstExp eval(AstExp x, Environment env) {
        return evaluate(x, env, true)._1;
    }

    private Tuple2<AstExp, Environment> evaluate(AstExp x, Environment env, boolean followSymbols) {
        if (x instanceof AstList) {
            return evaluateList((AstList)x, env, followSymbols);
        }
        if (followSymbols && x instanceof AstSymbol) {
            var symbol = (AstSymbol)x;
            return Tuple.of(env.lookup(symbol.asSymbol()).evaluate(List.of()), env);
        }

        return Tuple.of(x, env);
    }

    private Tuple2<AstExp, Environment> evaluateList(AstList x, Environment env, boolean followSymbols) {
        var list = x.value();
        if (list.size() == 0) {
            return Tuple.of(x, env);
        }
        var head = list.head();
        if (head instanceof AstList) {
            var procHead = evaluate(head, env, false);
            return evaluateProc((AstSymbol)procHead._1, list.tail(), procHead._2, followSymbols);
        }
        switch (((AstSymbol)head).asSymbol()) {
            case "if":
                var test = (AstAtom)(evaluate(list.get(1), env, followSymbols)._1);
                var isFalse = test.value().equals(0);
                var branch = isFalse ? list.get(3) : list.get(2);
                return Tuple.of(evaluate(branch, env, followSymbols)._1, env);
            case "define":
                var symbol = ((AstAtom)list.get(1)).asSymbol();
                var exp = (AstAtom)evaluate(list.get(2), env, followSymbols)._1;
                return Tuple.of(exp, env.add(symbol, Fn.value(exp)));
            case "lambda":
                var params = ((AstList)list.get(1)).value().map(v -> (AstSymbol)v);
                var body = list.get(2);
                return Tuple.of(new AstLambda(params, body, env), env);
            case "quote":
                return Tuple.of(list.get(1), env);
            default:
                return evaluateProc((AstSymbol)head, list.tail(), env, followSymbols);
        }
    }

    private Tuple2<AstExp, Environment> evaluateProc(AstSymbol head, List<AstExp> tail, Environment env, boolean followSymbols) {
        var fn = env.lookup(head.asSymbol());
        var params = tail
            .foldLeft(
                Tuple.<List<AstAtom>, Environment>of(List.empty(), env),
                (acc, curr) -> {
                    var accList = acc._1;
                    var accEnv = acc._2;
                    var result = evaluate(curr, accEnv, followSymbols);
                    return Tuple.of(accList.append((AstAtom)result._1), result._2);
                })
            ._1;
        return Tuple.of(fn.evaluate(params), env);
    }
}

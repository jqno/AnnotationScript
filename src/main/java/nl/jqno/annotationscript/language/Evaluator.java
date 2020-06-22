package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class Evaluator {
    public AstExp eval(AstExp x, Environment env) {
        return evaluate(x, env)._1;
    }

    private Tuple2<AstExp, Environment> evaluate(AstExp x, Environment env) {
        if (x instanceof AstList) {
            return evaluateList((AstList)x, env);
        }
        if (x instanceof AstSymbol) {
            var symbol = (AstSymbol)x;
            return Tuple.of(env.lookup(symbol.asSymbol()).evaluate(List.of()), env);
        }

        return Tuple.of(x, env);
    }

    private Tuple2<AstExp, Environment> evaluateList(AstList x, Environment env) {
        var list = x.value();
        if (list.size() == 0 || !(list.head() instanceof AstSymbol)) {
            return Tuple.of(x, env);
        }
        var head = (AstSymbol)list.head();
        switch (head.asSymbol()) {
            case "if":
                var test = (AstAtom)(evaluate(list.get(1), env)._1);
                var isFalse = test.value().equals(0);
                var branch = isFalse ? list.get(3) : list.get(2);
                return Tuple.of(evaluate(branch, env)._1, env);
            case "define":
                var symbol = ((AstAtom)list.get(1)).asSymbol();
                var exp = (AstAtom)evaluate(list.get(2), env)._1;
                return Tuple.of(exp, env.add(symbol, exp));
            default:
                var fnName = ((AstSymbol)list.get(0)).asSymbol();
                var evaluations = list
                    .drop(1)
                    .foldLeft(
                            Tuple.<List<AstAtom>, Environment>of(List.empty(), env),
                            (acc, curr) -> {
                                var accList = acc._1;
                                var accEnv = acc._2;
                                var result = evaluate(curr, accEnv);
                                return Tuple.of(accList.append((AstAtom)result._1), result._2);
                            })
                    ._1;
                var fn = env.lookup(fnName);
                return Tuple.of(fn.evaluate(evaluations), env);
        }
    }
}

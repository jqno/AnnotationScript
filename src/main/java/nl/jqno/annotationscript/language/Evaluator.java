package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;
import nl.jqno.annotationscript.language.fn.*;

public class Evaluator {
    public Object eval(AstExp x, Environment env) {
        return evaluate(x, env)._1;
    }

    public Tuple2<Object, Environment> evaluate(AstExp x, Environment env) {
        if (x instanceof AstSymbol) {
            var fn = env.lookup(x.asSymbol());
            if (fn instanceof Value) {
                return Tuple.of(fn.value(), env);
            }
            return Tuple.of(fn, env);
        }
        if (x instanceof AstInt || x instanceof AstFloat || x instanceof AstString) {
            return Tuple.of(x.value(), env);
        }

        var list = ((AstList)x).value();
        if (list.size() == 0) {
            return Tuple.of(list, env);
        }
        var head = list.head() instanceof AstSymbol ? list.head().asSymbol() : "";
        switch (head) {
            case "if":
                var test = evaluate(list.get(1), env)._1;
                var isFalse = test.equals(0);
                var branch = isFalse ? list.get(3) : list.get(2);
                return Tuple.of(evaluate(branch, env)._1, env);
            case "define":
                var symbol = list.get(1).asSymbol();
                var exp = evaluate(list.get(2), env)._1;
                var fn = exp instanceof Fn ? (Fn)exp : Fn.value(exp);
                return Tuple.of(exp, env.add(symbol, fn));
            case "lambda":
                var params = ((AstList)list.get(1)).value().map(v -> (AstSymbol)v);
                var body = list.get(2);
                var result = new Lambda(params, body, env);
                return Tuple.of(result, env);
            case "quote":
                return Tuple.of(list.get(1), env);
            default:
                return evaluateProc(list, env);
        }
    }

    private Tuple2<Object, Environment> evaluateProc(List<AstExp> list, Environment env) {
        var proc = evaluate(list.head(), env)._1;
        var args = list
            .tail()
            .foldLeft(Tuple.<List<Object>, Environment>of(List.empty(), env), (acc, curr) -> {
                var result = evaluate(curr, acc._2);
                return Tuple.of(acc._1.append(result._1), result._2);
            })
            ._1;
        if (proc instanceof Fn) {
            var fn = (Fn)proc;
            return Tuple.of(fn.evaluate(args, env, this), env);
        }
        return null;
    }
}

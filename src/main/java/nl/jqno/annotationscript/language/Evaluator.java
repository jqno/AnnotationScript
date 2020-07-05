package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;
import nl.jqno.annotationscript.language.fn.*;

public class Evaluator {
    public Object eval(AstExp exp, Environment env) {
        return evaluate(exp, env)._1;
    }

    public Tuple2<Object, Environment> evaluate(AstExp exp, Environment env) {
        if (exp instanceof AstSymbol) {
            return evaluateSymbol(exp, env);
        }
        if (exp instanceof AstInt || exp instanceof AstFloat || exp instanceof AstString) {
            return evaluateAtom(exp, env);
        }
        return evaluateList(exp, env);
    }

    private Tuple2<Object, Environment> evaluateSymbol(AstExp exp, Environment env) {
        var fn = env.lookup(exp.asSymbol());
        if (fn instanceof Value) {
            return Tuple.of(fn.value(), env);
        }
        return Tuple.of(fn, env);
    }

    private Tuple2<Object, Environment> evaluateAtom(AstExp exp, Environment env) {
        return Tuple.of(exp.value(), env);
    }

    private Tuple2<Object, Environment> evaluateList(AstExp exp, Environment env) {
        var list = ((AstList)exp).value();
        if (list.size() == 0) {
            return Tuple.of(list, env);
        }
        var head = list.head() instanceof AstSymbol ? list.head().asSymbol() : "";
        switch (head) {
            case "if":
                return evaluateIf(list, env);
            case "define":
                return evaluateDefine(list, env);
            case "lambda":
                return evaluateLambda(list, env);
            case "quote":
                return evaluateQuote(list, env);
            default:
                return evaluateProc(list, env);
        }
    }

    private Tuple2<Object, Environment> evaluateIf(List<AstExp> list, Environment env) {
        var test = evaluate(list.get(1), env)._1;
        var isFalse = test.equals(0);
        var branch = isFalse ? list.get(3) : list.get(2);
        return Tuple.of(evaluate(branch, env)._1, env);
    }

    private Tuple2<Object, Environment> evaluateDefine(List<AstExp> list, Environment env) {
        var symbol = list.get(1).asSymbol();
        var exp = evaluate(list.get(2), env)._1;
        var fn = exp instanceof Fn ? (Fn)exp : Fn.value(exp);
        return Tuple.of(exp, env.add(symbol, fn));
    }

    private Tuple2<Object, Environment> evaluateLambda(List<AstExp> list, Environment env) {
        var params = ((AstList)list.get(1)).value().map(v -> (AstSymbol)v);
        var body = list.get(2);
        var result = new Lambda(params, body, env);
        return Tuple.of(result, env);
    }

    private Tuple2<Object, Environment> evaluateQuote(List<AstExp> list, Environment env) {
        return Tuple.of(list.get(1), env);
    }

    private Tuple2<Object, Environment> evaluateProc(List<AstExp> list, Environment env) {
        var fn = (Fn)evaluate(list.head(), env)._1;
        var args = list
            .tail()
            .foldLeft(Tuple.<List<Object>, Environment>of(List.empty(), env), (acc, curr) -> {
                var result = evaluate(curr, acc._2);
                return Tuple.of(acc._1.append(result._1), result._2);
            })
            ._1;
        return Tuple.of(fn.evaluate(args, env, this), env);
    }
}

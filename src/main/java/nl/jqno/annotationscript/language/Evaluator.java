package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.exceptions.EvaluationException;
import nl.jqno.annotationscript.language.fn.*;

public class Evaluator {
    public Object eval(Object exp, Environment env) {
        return evaluate(exp, env)._1;
    }

    private Tuple2<Object, Environment> evaluate(Object exp, Environment env) {
        if (exp instanceof Symbol) {
            return evaluateSymbol((Symbol)exp, env);
        }
        if (exp instanceof Integer || exp instanceof Double || exp instanceof String) {
            return evaluateAtom(exp, env);
        }
        return evaluateList((List<?>)exp, env);
    }

    private Tuple2<Object, Environment> evaluateSymbol(Symbol exp, Environment env) {
        var fn = env.lookup(exp.name);
        if (fn instanceof Value) {
            return Tuple.of(fn.value(), env);
        }
        return Tuple.of(fn, env);
    }

    private Tuple2<Object, Environment> evaluateAtom(Object exp, Environment env) {
        return Tuple.of(exp, env);
    }

    private Tuple2<Object, Environment> evaluateList(List<?> exp, Environment env) {
        if (exp.size() == 0) {
            return Tuple.of(exp, env);
        }
        var head = exp.head() instanceof Symbol ? symbolName(exp.head()) : "";
        switch (head) {
            case "if":
                return evaluateIf(exp, env);
            case "cond":
                return evaluateCond(exp, env);
            case "define":
                return evaluateDefine(exp, env);
            case "lambda":
                return evaluateLambda(exp, env);
            case "quote":
                return evaluateQuote(exp, env);
            default:
                return evaluateProc(exp, env);
        }
    }

    private Tuple2<Object, Environment> evaluateIf(List<?> list, Environment env) {
        var test = evaluate(list.get(1), env)._1;
        var branch = isTruthy(test) ? list.get(2) : list.get(3);
        return Tuple.of(evaluate(branch, env)._1, env);
    }

    private Tuple2<Object, Environment> evaluateCond(List<?> list, Environment env) {
        var branch = list
            .drop(1)
            .sliding(2, 2)
            .find(lst -> isTruthy(evaluate(lst.get(0), env)._1))
            .getOrElseThrow(() -> new EvaluationException("cond has no true branch"))
            .get(1);
        return Tuple.of(evaluate(branch, env)._1, env);
    }

    private Tuple2<Object, Environment> evaluateDefine(List<?> list, Environment env) {
        var symbol = symbolName(list.get(1));
        var exp = evaluate(list.get(2), env)._1;
        var fn = exp instanceof Fn ? (Fn)exp : Fn.val(symbol, exp);
        return Tuple.of(exp, env.add(symbol, fn));
    }

    private Tuple2<Object, Environment> evaluateLambda(List<?> list, Environment env) {
        var params = ((List<?>)list.get(1)).map(v -> (Symbol)v);
        var body = list.get(2);
        var result = Fn.lambda(params, body, env);
        return Tuple.of(result, env);
    }

    private Tuple2<Object, Environment> evaluateQuote(List<?> list, Environment env) {
        return Tuple.of(list.get(1), env);
    }

    private Tuple2<Object, Environment> evaluateProc(List<?> list, Environment env) {
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

    private boolean isTruthy(Object x) {
        return !(x.equals(0) || x.equals(0.0));
    }

    private String symbolName(Object symbol) {
        return ((Symbol)symbol).name;
    }
}

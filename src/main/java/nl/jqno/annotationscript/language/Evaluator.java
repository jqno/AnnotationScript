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

    // CHECKSTYLE OFF: NPathComplexity
    // CHECKSTYLE OFF: CyclomaticComplexity
    private Tuple2<Object, Environment> evaluate(Object exp, Environment env) {
        Object x = exp;
        Environment e = env;
        while (true) {
            if (isSymbol(x)) {
                return evaluateSymbol((Symbol)x, e);
            }
            else if (isAtom(x)) {
                return evaluateAtom(x, e);
            }
            else if (isEmptyList(x)) {
                return Tuple.of(x, e);
            }
            else if (isForm("if", x)) {
                var t = evaluateIf(x, e);
                x = t._1;
            }
            else if (isForm("cond", x)) {
                var t = evaluateCond(x, e);
                x = t._1;
            }
            else if (isForm("define", x)) {
                return evaluateDefine(x, e);
            }
            else if (isForm("lambda", x)) {
                return evaluateLambda(x, e);
            }
            else if (isForm("quote", x)) {
                return evaluateQuote(x, e);
            }
            else if (isForm("begin", x)) {
                var t = evaluateBegin(x, e);
                x = t._1;
                e = t._2;
            }
            else {
                var t = evaluateProc(x, e);
                var fn = t._1;
                var args = t._2;

                if (fn instanceof Lambda l) {
                    x = l.getBody();
                    e = l.combine(args, e);
                }
                else {
                    return Tuple.of(fn.evaluate(args, e, this), e);
                }
            }
        }
    }
    // CHECKSTYLE ON: CyclomaticComplexity
    // CHECKSTYLE ON: NPathComplexity

    private Tuple2<Object, Environment> evaluateSymbol(Symbol exp, Environment env) {
        var fn = env.lookup(exp);
        if (fn instanceof Value) {
            return Tuple.of(fn.value(), env);
        }
        return Tuple.of(fn, env);
    }

    private Tuple2<Object, Environment> evaluateAtom(Object exp, Environment env) {
        return Tuple.of(exp, env);
    }

    private Tuple2<Object, Environment> evaluateIf(Object exp, Environment env) {
        var list = (List<?>)exp;
        var test = evaluate(list.get(1), env)._1;
        var branch = isTruthy(test) ? list.get(2) : list.get(3);
        return Tuple.of(branch, env);
    }

    private Tuple2<Object, Environment> evaluateCond(Object exp, Environment env) {
        var list = (List<?>)exp;
        var branch = list
            .drop(1)
            .sliding(2, 2)
            .find(lst -> isTruthy(evaluate(lst.get(0), env)._1))
            .getOrElseThrow(() -> new EvaluationException("cond has no true branch"))
            .get(1);
        return Tuple.of(branch, env);
    }

    private Tuple2<Object, Environment> evaluateDefine(Object exp, Environment env) {
        var list = (List<?>)exp;
        var symbol = (Symbol)list.get(1);
        var def = evaluate(list.get(2), env)._1;
        var fn = def instanceof Fn expFn ? expFn : Fn.val(symbol.name(), def);
        return Tuple.of(def, env.add(symbol, fn));
    }

    private Tuple2<Object, Environment> evaluateLambda(Object exp, Environment env) {
        var list = (List<?>)exp;
        var params = ((List<?>)list.get(1)).map(v -> (Symbol)v);
        var body = list.get(2);
        var result = Fn.lambda(params, body, env);
        return Tuple.of(result, env);
    }

    private Tuple2<Object, Environment> evaluateQuote(Object exp, Environment env) {
        var list = (List<?>)exp;
        return Tuple.of(list.get(1), env);
    }

    private Tuple2<Object, Environment> evaluateBegin(Object exp, Environment env) {
        var list = (List<?>)exp;
        var evaluated = list
            .tail()
            .dropRight(1)
            .foldLeft(Tuple.<List<Object>, Environment>of(List.empty(), env), (acc, curr) -> {
                var result = evaluate(curr, acc._2);
                return Tuple.of(acc._1.append(result._1), result._2);
            });
        return Tuple.of(list.last(), evaluated._2);
    }

    private Tuple2<Fn, List<Object>> evaluateProc(Object exp, Environment env) {
        var list = (List<?>)exp;
        var exps = list.map(q -> evaluate(q, env)._1);
        var fn = (Fn)exps.head();
        var args = exps.tail();

        return Tuple.of(fn, args);
    }

    private boolean isSymbol(Object exp) {
        return exp instanceof Symbol;
    }

    private boolean isAtom(Object exp) {
        return exp instanceof Integer || exp instanceof Double || exp instanceof String;
    }

    private boolean isEmptyList(Object exp) {
        return ((List<?>)exp).isEmpty();
    }

    private boolean isForm(String name, Object exp) {
        return new Symbol(name).equals(((List<?>)exp).head());
    }

    private boolean isTruthy(Object exp) {
        return !(exp.equals(Symbol.FALSE) || exp.equals(0) || exp.equals(0.0));
    }
}

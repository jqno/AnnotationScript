package nl.jqno.annotationscript.language;

import java.util.Objects;
import java.util.function.Supplier;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.fn.Fn;

public final class GlobalEnvironment {
    private static final List<Tuple2<String, Fn>> GLOBAL = List.of(
        builtin("+", params -> params.foldLeft(0.0, (acc, curr) -> acc + toDouble(curr))),
        builtin("-", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc - toDouble(curr))),
        builtin("*", params -> params.foldLeft(1.0, (acc, curr) -> acc * toDouble(curr))),
        builtin("/", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc / toDouble(curr))),
        builtin("%", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc % toDouble(curr))),
        builtin(">", params -> bool(toDouble(params.get(0)) > toDouble(params.get(1)))),
        builtin("<", params -> bool(toDouble(params.get(0)) < toDouble(params.get(1)))),
        builtin(">=", params -> bool(toDouble(params.get(0)) >= toDouble(params.get(1)))),
        builtin("<=", params -> bool(toDouble(params.get(0)) <= toDouble(params.get(1)))),
        builtin("=", params -> bool(isEquals(params))),
        builtin("abs", params -> Math.abs(toDouble(params.get(0)))),
        builtin("and", params -> bool(params.foldLeft(true, (acc, curr) -> acc && isTruthy(curr)))),
        builtin("append", params -> toList(() -> params.get(1)).map(l -> l.append(params.get(0))).getOrNull()),
        builtin("apply", (params, env, eval) -> toFn(params.get(0)).evaluate(toList(() -> params.get(1)).get(), env, eval)),
        builtin("atom?", params -> bool(isNumber(params.get(0)) || isString(params.get(0)))),
        builtin("begin", params -> params.last()),
        builtin("cons", params -> toList(() -> params.get(1)).map(l -> l.prepend(params.get(0))).getOrNull()),
        builtin("head", params -> toList(() -> params.get(0)).flatMap(l -> l.headOption()).getOrNull()),
        builtin("fold-left", (params, env, eval) ->
            toList(() -> params.get(2)).get().foldLeft(params.get(1), (acc, curr) -> toFn(params.get(0)).evaluate(List.of(acc, curr), env, eval))),
        builtin("length", params -> toList(() -> params.get(0)).map(l -> l.length()).getOrNull()),
        builtin("list", params -> params),
        builtin("list?", params -> bool(params.get(0) instanceof List)),
        builtin("map", (params, env, eval) -> toList(() -> params.get(1)).get().map(p -> (toFn(params.get(0))).evaluate(List.of(p), env, eval))),
        builtin("max", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> Math.max(acc, toDouble(curr)))),
        builtin("min", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> Math.min(acc, toDouble(curr)))),
        builtin("not", params -> bool(!isTruthy(params.get(0)))),
        builtin("null", (Object)null),
        builtin("null?", params -> bool(params.get(0) == null)),
        builtin("number?", params -> bool(isNumber(params.get(0)))),
        builtin("or", params -> bool(params.foldLeft(false, (acc, curr) -> acc || isTruthy(curr)))),
        builtin("pi", Math.PI),
        // CHECKSTYLE OFF: Regexp
        builtin("println", params -> { System.out.println(params.map(p -> toString(p)).mkString(" ")); return null; }),
        builtin("println-err", params -> { System.err.println(params.map(p -> toString(p)).mkString(" ")); return null; }),
        // CHECKSTYLE ON: Regexp
        builtin("procedure?", (params, env, eval) ->
            bool(env.lookupOption(params.get(0).toString()).map(Fn::isProcedure).getOrElse(false))),
        builtin("range", params -> List.range(toInt(params.get(0)), toInt(params.get(1)))),
        builtin("round", params -> toDouble(Math.round(toDouble(params.get(0))))),
        builtin("string?", params -> bool(isString(params.get(0)))),
        builtin("symbol?", params -> bool(isSymbol(params.get(0)))),
        builtin("tail", params -> toList(() -> params.get(0)).map(l -> l.size() > 0 ? l.tail() : List.empty()).getOrNull())
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}

    private static Tuple2<String, Fn> builtin(String name, Function1<List<Object>, Object> fn) {
        return Tuple.of(name, Fn.builtin(name, fn));
    }

    private static Tuple2<String, Fn> builtin(String name, Function3<List<Object>, Environment, Evaluator, Object> fn) {
        return Tuple.of(name, Fn.builtin(name, fn));
    }

    private static Tuple2<String, Fn> builtin(String name, Object value) {
        return Tuple.of(name, Fn.val(name, value));
    }

    private static int bool(boolean b) {
        return b ? 1 : 0;
    }

    private static boolean isEquals(List<Object> params) {
        Object one = params.get(0);
        Object two = params.get(1);
        if (one instanceof Number && two instanceof Number) {
            return Double.compare(toDouble(one), toDouble(two)) == 0;
        }
        return Objects.equals(one, two);
    }

    private static boolean isNumber(Object object) {
        return object instanceof Integer || object instanceof Double;
    }

    private static boolean isString(Object object) {
        if (object instanceof String) {
            var s = (String)object;
            return s.startsWith("'") && s.endsWith("'");
        }
        return false;
    }

    private static boolean isSymbol(Object object) {
        if (object instanceof String) {
            var s = (String)object;
            return !(s.startsWith("'") && s.endsWith("'"));
        }
        return false;
    }

    private static boolean isTruthy(Object x) {
        return !(x.equals(0.0) || x.equals(0));
    }

    private static double toDouble(Object x) {
        return Double.valueOf(x.toString());
    }

    private static Fn toFn(Object object) {
        return (Fn)object;
    }

    private static int toInt(Object x) {
        return Integer.valueOf(x.toString());
    }

    private static String toString(Object x) {
        if (isString(x)) {
            String str = (String)x;
            return str.substring(1, str.length()-1);
        }
        return x.toString();
    }

    @SuppressWarnings("unchecked")
    private static Option<List<Object>> toList(Supplier<Object> object) {
        try {
            var supplied = object.get();
            if (supplied instanceof List) {
                return Option.some((List<Object>)supplied);
            }
            return Option.none();
        }
        catch (RuntimeException ignored) {
            return Option.none();
        }
    }
}

package nl.jqno.annotationscript.language;

import java.util.Objects;
import java.util.function.Supplier;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
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
        builtin("map/contains?", params -> bool(toMap(params.get(0)).containsKey(params.get(1)))),
        builtin("map/empty", params -> HashMap.empty()),
        builtin("map/entries", params -> toMap(params.get(0)).toList().map(tup -> List.of(tup._1, tup._2))),
        builtin("map/get", params -> toMap(params.get(0)).getOrElse(params.get(1), null)),
        builtin("map/keys", params -> toMap(params.get(0)).keySet().toList()),
        builtin("map/merge", params -> toMap(params.get(0)).merge(toMap(params.get(1)))),
        builtin("map/of", params -> HashMap.ofEntries(params.sliding(2, 2).map(e -> Tuple.of(e.get(0), e.get(1))))),
        builtin("map/put", params -> toMap(params.get(0)).put(params.get(1), params.get(2))),
        builtin("map/remove", params -> toMap(params.get(0)).remove(params.get(1))),
        builtin("map/size", params -> toMap(params.get(0)).size()),
        builtin("map/values", params -> toMap(params.get(0)).values().toList()),
        builtin("max", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> Math.max(acc, toDouble(curr)))),
        builtin("min", params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> Math.min(acc, toDouble(curr)))),
        builtin("not", params -> bool(!isTruthy(params.get(0)))),
        builtin("null", (Object)null),
        builtin("null?", params -> bool(params.get(0) == null)),
        builtin("number?", params -> bool(isNumber(params.get(0)))),
        builtin("or", params -> bool(params.foldLeft(false, (acc, curr) -> acc || isTruthy(curr)))),
        builtin("pi", Math.PI),
        // CHECKSTYLE OFF: Regexp
        builtin("println", params -> { System.out.println(params.map(p -> unwrapString(p)).mkString(" ")); return null; }),
        builtin("println-err", params -> { System.err.println(params.map(p -> unwrapString(p)).mkString(" ")); return null; }),
        // CHECKSTYLE ON: Regexp
        builtin("procedure?", (params, env, eval) ->
            bool(env.lookupOption(params.get(0).toString()).map(Fn::isProcedure).getOrElse(false))),
        builtin("range", params -> List.range(toInt(params.get(0)), toInt(params.get(1)))),
        builtin("reverse", params -> toList(() -> params.get(0)).get().reverse()),
        builtin("round", params -> toDouble(Math.round(toDouble(params.get(0))))),
        builtin("str/char-at", params -> wrapString("" + unwrapString(params.get(1)).charAt(toInt(params.get(0))))),
        builtin("str/concat", params -> wrapString(params.foldLeft("", (acc, curr) -> acc + unwrapString(curr)))),
        builtin("str/ends-with?", params -> bool(unwrapString(params.get(1)).endsWith(unwrapString(params.get(0))))),
        builtin("str/index-of", params -> unwrapString(params.get(1)).indexOf(unwrapString(params.get(0)))),
        builtin("str/join", params -> wrapString(toList(() -> params.get(1)).get().map(s -> unwrapString(s)).mkString(unwrapString(params.get(0))))),
        builtin("str/length", params -> unwrapString(params.get(0)).length()),
        builtin("str/split", params -> List.of(unwrapString(params.get(1)).split(unwrapString(params.get(0)))).map(s -> wrapString(s))),
        builtin("str/starts-with?", params -> bool(unwrapString(params.get(1)).startsWith(unwrapString(params.get(0))))),
        builtin("str/to-lower", params -> wrapString(unwrapString(params.get(0)).toLowerCase())),
        builtin("str/to-upper", params -> wrapString(unwrapString(params.get(0)).toUpperCase())),
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
        if (x instanceof Double) {
            return ((Double)x).intValue();
        }
        return Integer.valueOf(x.toString());
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

    @SuppressWarnings("unchecked")
    private static Map<Object, Object> toMap(Object object) {
        return (Map<Object, Object>)object;
    }

    private static String unwrapString(Object x) {
        if (isString(x)) {
            String str = (String)x;
            return str.substring(1, str.length()-1);
        }
        return x.toString();
    }

    private static String wrapString(String x) {
        return "'" + x + "'";
    }
}

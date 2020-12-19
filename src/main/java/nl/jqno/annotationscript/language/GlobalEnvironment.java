package nl.jqno.annotationscript.language;

import static nl.jqno.annotationscript.language.Symbol.FALSE;
import static nl.jqno.annotationscript.language.Symbol.TRUE;

import java.util.Objects;
import java.util.function.Supplier;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.fn.Fn;

public final class GlobalEnvironment {
    private static final List<Tuple2<Symbol, Fn>> GLOBAL = List.of(
        builtin(TRUE.name, TRUE),
        builtin(FALSE.name, FALSE),
        builtin("+", params -> params.foldLeft((Object)0, (acc, curr) -> wideningOp((x, y) -> x + y, acc, curr))),
        builtin("-", params -> params.tail().foldLeft(params.head(), (acc, curr) -> wideningOp((x, y) -> x - y, acc, curr))),
        builtin("*", params -> params.foldLeft((Object)1, (acc, curr) -> wideningOp((x, y) -> x * y, acc, curr))),
        builtin("/", params -> params.tail().foldLeft(params.head(), (acc, curr) -> wideningOp((x, y) -> x / y, acc, curr))),
        builtin("%", params -> params.tail().foldLeft(params.head(), (acc, curr) -> wideningOp((x, y) -> x % y, acc, curr))),
        builtin(">", params -> bool(toDouble(params.get(0)) > toDouble(params.get(1)))),
        builtin("<", params -> bool(toDouble(params.get(0)) < toDouble(params.get(1)))),
        builtin(">=", params -> bool(toDouble(params.get(0)) >= toDouble(params.get(1)))),
        builtin("<=", params -> bool(toDouble(params.get(0)) <= toDouble(params.get(1)))),
        builtin("=", params -> bool(isEquals(params))),
        builtin("abs", params ->
            params.get(0) instanceof Integer ? (Object)Math.abs(toInt(params.get(0))) : (Object)Math.abs(toDouble(params.get(0)))),
        builtin("and", params -> bool(params.foldLeft(true, (acc, curr) -> acc && isTruthy(curr)))),
        builtin("append", params -> toList(() -> params.get(1)).map(l -> l.append(params.get(0))).getOrNull()),
        builtin("apply", (params, env, eval) -> toFn(params.get(0)).evaluate(toList(() -> params.get(1)).get(), env, eval)),
        builtin("atom?", params -> bool(isNumber(params.get(0)) || isString(params.get(0)) || isSymbol(params.get(0)))),
        builtin("begin", params -> params.last()),
        builtin("cons", params -> toList(() -> params.get(1)).map(l -> l.prepend(params.get(0))).getOrNull()),
        builtin("contains?", params -> bool(toList(() -> params.get(0)).get().contains(params.get(1)))),
        builtin("dec", params -> wideningOp((x, y) -> x - y, params.get(0), 1)),
        builtin("else", TRUE),
        builtin("empty?", params -> {
            var p = params.get(0);
            if (p instanceof String) { return bool(toString(p).isEmpty()); }
            if (p instanceof List) { return bool(toList(() -> p).get().isEmpty()); }
            if (p instanceof Map) { return bool(toMap(p).isEmpty()); }
            return null;
        }),
        builtin("filter", (params, env, eval) -> 
            toList(() -> params.get(1)).get().filter(p -> isTruthy((toFn(params.get(0))).evaluate(List.of(p), env, eval)))),
        builtin("fold-left", (params, env, eval) ->
            toList(() -> params.get(2)).get().foldLeft(params.get(1), (acc, curr) -> toFn(params.get(0)).evaluate(List.of(acc, curr), env, eval))),
        builtin("head", params -> toList(() -> params.get(0)).flatMap(l -> l.headOption()).getOrNull()),
        builtin("inc", params -> wideningOp((x, y) -> x + y, params.get(0), 1)),
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
        builtin("max", params -> params.tail().foldLeft(params.head(), (acc, curr) -> wideningOp(Math::max, acc, curr))),
        builtin("min", params -> params.tail().foldLeft(params.head(), (acc, curr) -> wideningOp(Math::min, acc, curr))),
        builtin("not", params -> bool(!isTruthy(params.get(0)))),
        builtin("null", (Object)null),
        builtin("null?", params -> bool(params.get(0) == null)),
        builtin("number?", params -> bool(isNumber(params.get(0)))),
        builtin("or", params -> bool(params.foldLeft(false, (acc, curr) -> acc || isTruthy(curr)))),
        builtin("parse-float", params ->
            { try { return Double.parseDouble(toString(params.get(0))); } catch (NumberFormatException e) { return null; } }),
        builtin("parse-int", params -> 
            { try { return Integer.parseInt(toString(params.get(0))); } catch (NumberFormatException e) { return null; } }),
        builtin("pi", Math.PI),
        // CHECKSTYLE OFF: Regexp
        builtin("println", params -> { System.out.println(params.map(p -> toString(p)).mkString(" ")); return null; }),
        builtin("println-err", params -> { System.err.println(params.map(p -> toString(p)).mkString(" ")); return null; }),
        // CHECKSTYLE ON: Regexp
        builtin("procedure?", (params, env, eval) ->
            bool(env.lookupOption(toSymbol(params.get(0))).map(Fn::isProcedure).getOrElse(false))),
        builtin("range", params -> List.range(toInt(params.get(0)), toInt(params.get(1)))),
        builtin("reverse", params -> toList(() -> params.get(0)).get().reverse()),
        builtin("round", params -> toInt(Math.round(toDouble(params.get(0))))),
        builtin("str/char-at", params -> "" + toString(params.get(1)).charAt(toInt(params.get(0)))),
        builtin("str/concat", params -> params.foldLeft("", (acc, curr) -> acc + toString(curr))),
        builtin("str/ends-with?", params -> bool(toString(params.get(1)).endsWith(toString(params.get(0))))),
        builtin("str/index-of", params -> toString(params.get(1)).indexOf(toString(params.get(0)))),
        builtin("str/join", params -> toList(() -> params.get(1)).get().map(s -> toString(s)).mkString(toString(params.get(0)))),
        builtin("str/length", params -> toString(params.get(0)).length()),
        builtin("str/replace", params -> toString(params.get(0)).replace(toString(params.get(1)), toString(params.get(2)))),
        builtin("str/split", params -> List.of(toString(params.get(1)).split(toString(params.get(0))))),
        builtin("str/starts-with?", params -> bool(toString(params.get(1)).startsWith(toString(params.get(0))))),
        builtin("str/substring", params -> toString(params.get(0)).substring(toInt(params.get(1)), toInt(params.get(2)))),
        builtin("str/to-lower", params -> toString(params.get(0)).toLowerCase()),
        builtin("str/to-upper", params -> toString(params.get(0)).toUpperCase()),
        builtin("string?", params -> bool(isString(params.get(0)))),
        builtin("symbol", params -> new Symbol(toString(params.get(0)))),
        builtin("symbol?", params -> bool(isSymbol(params.get(0)))),
        builtin("tail", params -> toList(() -> params.get(0)).map(l -> !l.isEmpty() ? l.tail() : List.empty()).getOrNull()),
        builtin("throw", params -> { throw new RuntimeException(toString(params.get(0))); })
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}

    private static Tuple2<Symbol, Fn> builtin(String name, Function1<List<Object>, Object> fn) {
        return Tuple.of(new Symbol(name), Fn.builtin(name, fn));
    }

    private static Tuple2<Symbol, Fn> builtin(String name, Function3<List<Object>, Environment, Evaluator, Object> fn) {
        return Tuple.of(new Symbol(name), Fn.builtin(name, fn));
    }

    private static Tuple2<Symbol, Fn> builtin(String name, Object value) {
        return Tuple.of(new Symbol(name), Fn.val(name, value));
    }

    private static Symbol bool(boolean b) {
        return b ? TRUE : FALSE;
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
        return object instanceof String;
    }

    private static boolean isSymbol(Object object) {
        return object instanceof Symbol;
    }

    private static boolean isTruthy(Object x) {
        return !(x.equals(FALSE) || x.equals(0.0) || x.equals(0));
    }

    private static Double toDouble(Object x) {
        if (x instanceof Double) {
            return (Double)x;
        }
        if (x instanceof Integer) {
            return Double.valueOf((Integer)x);
        }
        if (x instanceof Symbol) {
            return Double.valueOf(((Symbol)x).name);
        }
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

    private static String toString(Object x) {
        return x.toString();
    }

    private static Symbol toSymbol(Object x) {
        return x instanceof Symbol ? (Symbol)x : null;
    }

    private static Object wideningOp(Function2<Double, Double, Object> op, Object x, Object y) {
        var result = op.apply(toDouble(x), toDouble(y));
        if (x instanceof Integer && y instanceof Integer) {
            return toInt(result);
        }
        return result;
    }
}

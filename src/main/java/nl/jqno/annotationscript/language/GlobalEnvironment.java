package nl.jqno.annotationscript.language;

import java.util.Objects;
import java.util.function.Supplier;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.fn.Fn;

public final class GlobalEnvironment {
    private static final List<Tuple2<String, Fn>> GLOBAL = List.of(
        Tuple.of("+", Fn.builtin(params -> params.foldLeft(0.0, (acc, curr) -> acc + toDouble(curr)))),
        Tuple.of("-", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc - toDouble(curr)))),
        Tuple.of("*", Fn.builtin(params -> params.foldLeft(1.0, (acc, curr) -> acc * toDouble(curr)))),
        Tuple.of("/", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc / toDouble(curr)))),
        Tuple.of("%", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc % toDouble(curr)))),
        Tuple.of(">", Fn.builtin(params -> bool(toDouble(params.get(0)) > toDouble(params.get(1))))),
        Tuple.of("<", Fn.builtin(params -> bool(toDouble(params.get(0)) < toDouble(params.get(1))))),
        Tuple.of(">=", Fn.builtin(params -> bool(toDouble(params.get(0)) >= toDouble(params.get(1))))),
        Tuple.of("<=", Fn.builtin(params -> bool(toDouble(params.get(0)) <= toDouble(params.get(1))))),
        Tuple.of("=", Fn.builtin(params -> bool(Objects.equals(params.get(0), params.get(1))))),
        Tuple.of("append", Fn.builtin(params -> toList(() -> params.get(1)).map(l -> l.append(params.get(0))).getOrNull())),
        Tuple.of("begin", Fn.builtin(params -> params.last())),
        Tuple.of("cons", Fn.builtin(params -> toList(() -> params.get(1)).map(l -> l.prepend(params.get(0))).getOrNull())),
        Tuple.of("head", Fn.builtin(params -> toList(() -> params.get(0)).flatMap(l -> l.headOption()).getOrNull())),
        Tuple.of("length", Fn.builtin(params -> toList(() -> params.get(0)).map(l -> l.length()).getOrNull())),
        Tuple.of("list", Fn.builtin(params -> params)),
        Tuple.of("list?", Fn.builtin(params -> bool(params.get(0) instanceof List))),
        Tuple.of("null", Fn.val(null)),
        Tuple.of("null?", Fn.builtin(params -> bool(params.get(0) == null))),
        Tuple.of("pi", Fn.val(Math.PI)),
        // CHECKSTYLE OFF: Regexp
        Tuple.of("println", Fn.builtin(params -> { System.out.println(params.mkString(" ")); return null; })),
        Tuple.of("println-err", Fn.builtin(params -> { System.err.println(params.mkString(" ")); return null; })),
        // CHECKSTYLE ON: Regexp
        Tuple.of("tail", Fn.builtin(params -> toList(() -> params.get(0)).map(l -> l.size() > 0 ? l.tail() : List.empty()).getOrNull()))
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}

    private static int bool(boolean b) {
        return b ? 1 : 0;
    }

    private static double toDouble(Object x) {
        return Double.valueOf(x.toString());
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

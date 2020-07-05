package nl.jqno.annotationscript.language;

import java.util.Objects;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.fn.Fn;

public final class GlobalEnvironment {
    private static final List<Tuple2<String, Fn>> GLOBAL = List.of(
        Tuple.of("+", Fn.builtin(params -> params.foldLeft(0.0, (acc, curr) -> acc + toDouble(curr)))),
        Tuple.of("-", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc - toDouble(curr)))),
        Tuple.of("*", Fn.builtin(params -> params.foldLeft(1.0, (acc, curr) -> acc * toDouble(curr)))),
        Tuple.of("/", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc / toDouble(curr)))),
        Tuple.of("%", Fn.builtin(params -> params.tail().foldLeft(toDouble(params.head()), (acc, curr) -> acc % toDouble(curr)))),
        Tuple.of(">", Fn.builtin(params -> toDouble(params.get(0)) > toDouble(params.get(1)) ? 1 : 0)),
        Tuple.of("<", Fn.builtin(params -> toDouble(params.get(0)) < toDouble(params.get(1)) ? 1 : 0)),
        Tuple.of(">=", Fn.builtin(params -> toDouble(params.get(0)) >= toDouble(params.get(1)) ? 1 : 0)),
        Tuple.of("<=", Fn.builtin(params -> toDouble(params.get(0)) <= toDouble(params.get(1)) ? 1 : 0)),
        Tuple.of("=", Fn.builtin(params -> Objects.equals(params.get(0), params.get(1)) ? 1 : 0)),
        Tuple.of("begin", Fn.builtin(params -> params.last())),
        Tuple.of("pi", Fn.val(Math.PI)),
        // CHECKSTYLE OFF: Regexp
        Tuple.of("println", Fn.builtin(params -> { System.out.println(params.mkString(" ")); return null; })),
        Tuple.of("println-err", Fn.builtin(params -> { System.err.println(params.mkString(" ")); return null; }))
        // CHECKSTYLE ON: Regexp
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}

    private static double toDouble(Object x) {
        return Double.valueOf(x.toString());
    }
}

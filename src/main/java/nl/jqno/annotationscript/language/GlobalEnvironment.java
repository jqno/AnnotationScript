package nl.jqno.annotationscript.language;

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
        Tuple.of("begin", Fn.builtin(params -> params.last())),
        Tuple.of("pi", Fn.val(Math.PI))
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}

    private static double toDouble(Object x) {
        return Double.valueOf(x.toString());
    }
}

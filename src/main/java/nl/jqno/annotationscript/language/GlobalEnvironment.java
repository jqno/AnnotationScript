package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public final class GlobalEnvironment {
    private static final List<Tuple2<String, Fn>> GLOBAL = List.of(
        Tuple.of("+", new Fn(params -> new AstFloat(params.foldLeft(0.0, (acc, curr) -> acc + curr.asFloat())))),
        Tuple.of("-", new Fn(params -> new AstFloat(params.tail().foldLeft(params.head().asFloat(), (acc, curr) -> acc - curr.asFloat())))),
        Tuple.of("*", new Fn(params -> new AstFloat(params.foldLeft(1.0, (acc, curr) -> acc * curr.asFloat())))),
        Tuple.of("/", new Fn(params -> new AstFloat(params.tail().foldLeft(params.head().asFloat(), (acc, curr) -> acc / curr.asFloat())))),
        Tuple.of("begin", new Fn(params -> params.last())),
        Tuple.of("pi", new Fn(params -> new AstFloat(Math.PI)))
    );

    public static Environment build() {
        return new Environment(GLOBAL.toMap(t -> t));
    }

    private GlobalEnvironment() {}
}

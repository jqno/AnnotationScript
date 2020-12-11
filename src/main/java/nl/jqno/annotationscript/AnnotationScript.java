package nl.jqno.annotationscript;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.language.AnnotationTokenizer;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.Generator;
import nl.jqno.annotationscript.language.GlobalEnvironment;
import nl.jqno.annotationscript.language.Parser;
import nl.jqno.annotationscript.language.StringTokenizer;
import nl.jqno.annotationscript.language.Symbol;
import nl.jqno.annotationscript.language.fn.Fn;

public final class AnnotationScript {

    public static Object run(Class<?> source) {
        return run(source, HashMap.empty());
    }

    public static Object run(Class<?> source, Map<String, Object> initialValues) {
        var environment = initialValues.foldLeft(
                GlobalEnvironment.build(),
                (env, entry) -> env.add(new Symbol(entry._1), Fn.val(entry._1, entry._2)));
        var tokens = new AnnotationTokenizer(source).tokenize();
        var parsed = new Parser(tokens).parse();
        var evaluated = new Evaluator().eval(parsed, environment);
        return evaluated;
    }

    public static String generate(String source) {
        var tokens = new StringTokenizer(source).tokenize();
        var parsed = new Parser(tokens).parse();
        var generated = new Generator().generate(parsed);
        return generated;
    }

    private AnnotationScript() {}
}

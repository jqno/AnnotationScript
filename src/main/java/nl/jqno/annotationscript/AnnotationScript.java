package nl.jqno.annotationscript;

import nl.jqno.annotationscript.language.AnnotationTokenizer;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.Generator;
import nl.jqno.annotationscript.language.GlobalEnvironment;
import nl.jqno.annotationscript.language.Parser;
import nl.jqno.annotationscript.language.StringTokenizer;

public final class AnnotationScript {

    public static Object run(Class<?> source) {
        var environment = GlobalEnvironment.build();
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

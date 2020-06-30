package nl.jqno.annotationscript;

import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.GlobalEnvironment;
import nl.jqno.annotationscript.language.Parser;
import nl.jqno.annotationscript.language.Tokenizer;

public final class AnnotationScript {

    public static Object run(Class<?> source) {
        var environment = GlobalEnvironment.build();
        var tokens = new Tokenizer(source).tokenize();
        var parsed = new Parser(tokens).parse();
        var evaluated = new Evaluator().eval(parsed, environment);
        return evaluated.value();
    }

    private AnnotationScript() {}
}

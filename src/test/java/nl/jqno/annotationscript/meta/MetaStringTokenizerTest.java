package nl.jqno.annotationscript.meta;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.One;
import nl.jqno.annotationscript.Annotations.Zero;
import nl.jqno.annotationscript.abstract_test.StringTokenizerTest;

public class MetaStringTokenizerTest extends StringTokenizerTest {

    @Zero("begin")
    @Zero(include=StringTokenizer.class)
    @Zero(list={@One("tokenize"), @One("input")})
    class Tokenizer {}

    @Override
    @SuppressWarnings("unchecked")
    public List<String> tokenize(String input) {
        var initialValues = HashMap.<String, Object>of("input", input);
        return (List<String>)AnnotationScript.run(Tokenizer.class, initialValues);
    }
}

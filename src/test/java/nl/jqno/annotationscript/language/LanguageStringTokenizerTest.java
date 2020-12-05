package nl.jqno.annotationscript.language;

import io.vavr.collection.List;
import nl.jqno.annotationscript.abstract_test.StringTokenizerTest;

public class LanguageStringTokenizerTest extends StringTokenizerTest {

    @Override
    public List<String> tokenize(String input) {
        var tokenizer = new StringTokenizer(input);
        return tokenizer.tokenize();
    }
}

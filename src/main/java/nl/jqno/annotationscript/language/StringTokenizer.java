package nl.jqno.annotationscript.language;

import io.vavr.collection.List;

public class StringTokenizer implements Tokenizer {
    private final String source;

    public StringTokenizer(String source) {
        this.source = source;
    }

    @Override
    public List<String> tokenize() {
        var result = source
            .replace("(", " ( ")
            .replace(")", " ) ")
            .split(" ");
        return List.of(result)
            .filter(s -> !s.isEmpty());
    }
}

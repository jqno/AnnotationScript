package nl.jqno.annotationscript.language;

import io.vavr.collection.List;

public interface Tokenizer {
    public List<String> tokenize();
}

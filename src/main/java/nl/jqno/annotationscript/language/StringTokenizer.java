package nl.jqno.annotationscript.language;

import io.vavr.collection.List;

public class StringTokenizer implements Tokenizer {
    // CHECKSTYLE OFF: AvoidEscapedUnicodeCharacters
    private static final String PUNCTUATION_SPACE = "\u2008";
    // CHECKSTYLE ON: AvoidEscapedUnicodeCharacters

    private final String source;

    public StringTokenizer(String source) {
        this.source = source;
    }

    @Override
    public List<String> tokenize() {
        // This is awful. Instead of writing a proper tokenizer, we're splitting on spaces.
        // However, now we can't have spaces in string constants anymore.
        // So now it's required that a user escape the space, so we can replace it here
        // with another character. Then we can do the split as before, and afterward
        // we'll replace the character back to a normal space.
        var result = source
            .replace("\\ ", PUNCTUATION_SPACE)
            .replace("\n", " ")
            .replace("(", " ( ")
            .replace(")", " ) ")
            .split(" ");
        return List.of(result)
            .map(s -> s.replace(PUNCTUATION_SPACE, " "))
            .filter(s -> !s.isEmpty());
    }
}

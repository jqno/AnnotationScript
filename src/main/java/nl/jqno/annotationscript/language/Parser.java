package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;

public class Parser {
    private final List<String> allTokens;

    public Parser(List<String> allTokens) {
        this.allTokens = allTokens;
    }

    public AstExp parse() {
        return readFromTokens(allTokens)._1;
    }

    private Tuple2<AstExp, List<String>> readFromTokens(List<String> tokens) {
        if (tokens.size() == 0) {
            throw new ParseException("unexpected EOF");
        }
        var token = tokens.head();
        if ("(".equals(token)) {
            return list(tokens.tail());
        }
        if (")".equals(token)) {
            throw new ParseException("unexpected )");
        }
        return Tuple.of(atom(token), tokens.tail());
    }

    private Tuple2<AstExp, List<String>> list(List<String> remainingTokens) {
        var result = List.<AstExp>empty();

        var tokens = remainingTokens;
        var token = tokens.head();
        while (!")".equals(token)) {
            var parseResult = readFromTokens(tokens);
            result = result.append(parseResult._1);
            tokens = parseResult._2;
            token = tokens.head();
        }

        return Tuple.of(new AstList(result), tokens.tail());
    }

    private AstAtom<?> atom(String token) {
        try {
            return new AstInt(Integer.parseInt(token));
        }
        catch (NumberFormatException ignored) {
            return new AstSymbol(token);
        }
    }
}

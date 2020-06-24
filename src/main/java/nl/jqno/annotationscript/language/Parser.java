package nl.jqno.annotationscript.language;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.*;
import nl.jqno.annotationscript.language.exceptions.ParseException;

public class Parser {
    private final List<String> allTokens;

    public Parser(List<String> allTokens) {
        this.allTokens = allTokens;
    }

    public AstExp parse() {
        if (allTokens.size() == 0) {
            throw new ParseException("no input");
        }
        var parsed = readFromTokens(allTokens);
        if (parsed._2.size() > 0) {
            throw new ParseException("unexpected end of program");
        }
        return parsed._1;
    }

    private Tuple2<AstExp, List<String>> readFromTokens(List<String> tokens) {
        var token = tokens.head();
        if ("(".equals(token)) {
            return list(List.empty(), tokens.tail());
        }
        if (")".equals(token)) {
            throw new ParseException("unexpected )");
        }
        return Tuple.of(atom(token), tokens.tail());
    }

    private Tuple2<AstExp, List<String>> list(List<AstExp> accumulated, List<String> tokens) {
        if (tokens.size() == 0) {
            throw new ParseException("unexpected EOF");
        }
        var head = tokens.head();
        if (")".equals(head)) {
            return Tuple.of(new AstList(accumulated), tokens.tail());
        }
        var parsed = readFromTokens(tokens);
        return list(accumulated.append(parsed._1), parsed._2);
    }

    private AstAtom atom(String token) {
        if (token.startsWith("'") && token.endsWith("'")) {
            return new AstString(token.substring(1, token.length() - 1));
        }
        try {
            return new AstInt(Integer.parseInt(token));
        }
        catch (NumberFormatException ignored1) {
            try {
                return new AstFloat(Double.parseDouble(token));
            }
            catch (NumberFormatException ignored2) {
                return new AstSymbol(token);
            }
        }
    }
}

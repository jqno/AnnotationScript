package nl.jqno.annotationscript.language;

import io.vavr.collection.List;

public class Generator {
    private static final List<String> LEVELS =
        List.of("", "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven");

    public String generate(Object exp) {
        return generate(exp, 0);
    }

    private String generate(Object exp, int level) {
        if (exp instanceof List<?>) {
            return generateList((List<?>)exp, level);
        }
        if (exp instanceof Symbol) {
            return generateSymbol(exp, level);
        }
        if (exp instanceof String) {
            return generateString(exp, level);
        }
        return generateAtom(exp, level);
    }

    private String generateAtom(Object exp, int level) {
        return prefix(level) + "\"" + exp + "\")";
    }

    private String generateString(Object exp, int level) {
        return prefix(level) + "\"'" + exp + "'\")";
    }

    private String generateSymbol(Object exp, int level) {
        return prefix(level) + "\"" + ((Symbol)exp).name + "\")";
    }

    private String generateList(List<?> exp, int level) {
        if (level == 0) {
            return exp
                .map(e -> generate(e, level + 1))
                .mkString();
        }
        var inner = exp
            .map(e -> generate(e, level + 1))
            .mkString(", ");
        return prefix(level) + "list={" + inner + "})";
    }

    private String prefix(int level) {
        return "@" + LEVELS.get(level) + "(";
    }
}

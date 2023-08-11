package nl.jqno.annotationscript.language;

import io.vavr.collection.List;

public class Generator {
    private static final List<String> LEVELS =
        List.of("", "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven");

    public String generate(Object exp) {
        return generate(exp, 0);
    }

    private String generate(Object exp, int level) {
        if (exp instanceof List<?> l) {
            return generateList(l, level);
        }
        if (exp instanceof Symbol s) {
            return generateSymbol(s, level);
        }
        if (exp instanceof String s) {
            return generateString(s, level);
        }
        return generateAtom(exp, level);
    }

    private String generateAtom(Object exp, int level) {
        return prefix(level) + "\"" + exp + "\")";
    }

    private String generateString(String exp, int level) {
        return prefix(level) + "\"'" + exp + "'\")";
    }

    private String generateSymbol(Symbol exp, int level) {
        return prefix(level) + "\"" + exp.name + "\")";
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

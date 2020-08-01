package nl.jqno.annotationscript.language;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.ast.AstExp;
import nl.jqno.annotationscript.language.ast.AstList;

public class Generator {
    private static final List<String> LEVELS =
        List.of("", "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven");

    public String generate(AstExp exp) {
        return generate(exp, 0);
    }

    private String generate(AstExp exp, int level) {
        if (exp instanceof AstList) {
            return generateList((AstList)exp, level);
        }
        return generateAtom(exp, level);
    }

    private String generateAtom(AstExp exp, int level) {
        return prefix(level) + "\"" + exp.toString() + "\")";
    }

    private String generateList(AstList exp, int level) {
        if (level == 0) {
            return exp.value()
                .map(e -> generate(e, level + 1))
                .mkString();
        }
        var inner = exp.value()
            .map(e -> generate(e, level + 1))
            .mkString(", ");
        return prefix(level) + "list={" + inner + "})";
    }

    private String prefix(int level) {
        return "@" + LEVELS.get(level) + "(";
    }
}

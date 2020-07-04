package nl.jqno.annotationscript.language.fn;

import nl.jqno.annotationscript.language.ast.AstExp;

public final class Exp implements Fn {
    private final AstExp value;

    public Exp(AstExp value) {
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }
}

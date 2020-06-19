package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

public final class AstSymbol implements AstAtom<String> {
    private final String value;

    public AstSymbol(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstSymbol)) {
            return false;
        }
        AstSymbol other = (AstSymbol)obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

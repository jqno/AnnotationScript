package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

public final class AstString implements AstExp {
    private final String value;

    public AstString(String value) {
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstString)) {
            return false;
        }
        AstString other = (AstString)obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}


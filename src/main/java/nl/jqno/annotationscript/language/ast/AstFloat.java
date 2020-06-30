package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

public final class AstFloat implements AstExp {
    private final double value;

    public AstFloat(double value) {
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public double asFloat() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstFloat)) {
            return false;
        }
        AstFloat other = (AstFloat)obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}

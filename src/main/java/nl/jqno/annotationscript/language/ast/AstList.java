package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

import io.vavr.collection.List;

public final class AstList implements AstExp {
    private final List<AstExp> value;

    public AstList(List<AstExp> value) {
        this.value = value;
    }

    public List<AstExp> value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstList)) {
            return false;
        }
        AstList other = (AstList)obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

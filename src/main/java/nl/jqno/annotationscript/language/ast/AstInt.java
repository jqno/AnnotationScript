package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

public final class AstInt implements AstAtom<Integer> {
    private final int value;

    public AstInt(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstInt)) {
            return false;
        }
        AstInt other = (AstInt)obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

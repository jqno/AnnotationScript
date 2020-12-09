package nl.jqno.annotationscript.language;

import java.util.Objects;

public final class Symbol {
    public final String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol)) {
            return false;
        }
        Symbol other = (Symbol)obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "'" + name;
    }
}

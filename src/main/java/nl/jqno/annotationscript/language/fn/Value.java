package nl.jqno.annotationscript.language.fn;

public final class Value implements Fn {
    private final String name;
    private final Object value;

    Value(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean isProcedure() {
        return false;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String toString() {
        return "Value[" + name + ":" + value() + "]";
    }
}


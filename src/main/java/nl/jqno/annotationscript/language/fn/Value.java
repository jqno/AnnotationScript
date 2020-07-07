package nl.jqno.annotationscript.language.fn;

public final class Value implements Fn {
    private final Object value;

    Value(Object value) {
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
}


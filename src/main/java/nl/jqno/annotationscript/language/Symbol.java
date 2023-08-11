package nl.jqno.annotationscript.language;

public record Symbol(String name) {
    public static final Symbol TRUE = new Symbol("#t");
    public static final Symbol FALSE = new Symbol("#f");

    @Override
    public String toString() {
        return "'" + name;
    }
}

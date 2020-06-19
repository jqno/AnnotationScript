package nl.jqno.annotationscript.language.ast;

public interface AstAtom<T> extends AstExp {
    public T value();
}

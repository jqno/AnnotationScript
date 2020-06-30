package nl.jqno.annotationscript.language.ast;

import nl.jqno.annotationscript.language.exceptions.EvaluationException;

public interface AstExp {
    public Object value();

    public default int asInt() {
        throw new EvaluationException("not an int: " + value());
    }

    public default double asFloat() {
        throw new EvaluationException("not a float: " + value());
    }

    public default String asString() {
        throw new EvaluationException("not a string: " + value());
    }

    public default String asSymbol() {
        throw new EvaluationException("not a symbol: " + value());
    }
}

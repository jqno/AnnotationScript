package nl.jqno.annotationscript.language.ast;

import java.util.Objects;

import io.vavr.collection.List;
import nl.jqno.annotationscript.language.Environment;
import nl.jqno.annotationscript.language.Evaluator;
import nl.jqno.annotationscript.language.Fn;

public final class AstLambda implements AstExp {
    private final List<AstSymbol> params;
    private final AstExp body;
    private final Environment capturedEnv;

    public AstLambda(List<AstSymbol> params, AstExp body, Environment capturedEnv) {
        this.params = params;
        this.body = body;
        this.capturedEnv = capturedEnv;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AstLambda)) {
            return false;
        }
        AstLambda other = (AstLambda)obj;
        return Objects.equals(params, other.params)
            && Objects.equals(body, other.body)
            && Objects.equals(capturedEnv, other.capturedEnv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params, body, capturedEnv);
    }

    @Override
    public String toString() {
        return "[λ " + params.mkString(",") + "]";
    }
}

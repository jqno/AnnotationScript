package nl.jqno.annotationscript.language.ast;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.language.ast.AstAtomTest.AstBogus;
import nl.jqno.equalsverifier.EqualsVerifier;

public class AstExpTest {

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forPackage(AstExp.class.getPackageName())
            .except(AstBogus.class)
            .verify();
    }
}

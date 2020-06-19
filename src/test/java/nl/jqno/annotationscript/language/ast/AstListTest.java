package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class AstListTest {
    private static final List<AstExp> LIST = List.of(new AstInt(42), new AstSymbol("something"), new AstList(List.empty()));

    @Test
    public void value() {
        var sut = new AstList(LIST);
        assertEquals(LIST, sut.value());
    }

    @Test
    public void tostring() {
        var sut = new AstList(LIST);
        assertEquals(sut.toString(), "(42 something ())");
    }
}

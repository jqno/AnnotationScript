package nl.jqno.annotationscript.language.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class AstListTest {
    @Test
    public void value() {
        var list = List.of(new AstInt(42), new AstSymbol("something"), new AstList(List.empty()));
        var sut = new AstList(list);
        assertEquals(list, sut.value());
    }
}

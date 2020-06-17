package nl.jqno.annotationscript;

import nl.jqno.annotationscript.Annotations.Begin;
import nl.jqno.annotationscript.Annotations.End;
import nl.jqno.annotationscript.Annotations.Int;
import nl.jqno.annotationscript.Annotations.Sym;

public class AnnotationsTest {

    @Begin@Int(1)@Sym("if")@End
    public static class Holder {}
}

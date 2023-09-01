package nl.jqno.annotationscript.demo.meta.bf;

import io.vavr.collection.List;

public final class Converter {

    @SuppressWarnings("unchecked")
    public static String asciiArrayToString(Object input) {
        return ((List<Object>)input)
            .map(l -> Character.toString((char)((Integer)l).intValue()))
            .mkString();
    }
}

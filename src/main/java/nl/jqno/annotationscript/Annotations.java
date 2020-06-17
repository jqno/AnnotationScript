package nl.jqno.annotationscript;

import java.lang.annotation.*;

public class Annotations {
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface Begin {}

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface End {}

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface Int {
        int value();
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface Sym {
        String value();
    }
}

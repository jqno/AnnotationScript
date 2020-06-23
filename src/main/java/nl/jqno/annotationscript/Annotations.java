package nl.jqno.annotationscript;

import java.lang.annotation.*;

public final class Annotations {

    public static final String EMPTY = "";

    @Repeatable(ProgramHolder.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface One {
        String literal() default EMPTY;
        Two[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Two {
        String literal() default EMPTY;
        Three[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Three {
        String literal() default EMPTY;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ProgramHolder {
        One[] value();
    }

    private Annotations() {}
}

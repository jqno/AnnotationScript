package nl.jqno.annotationscript;

import java.lang.annotation.*;

public final class Annotations {

    public static final String EMPTY = "";

    @Repeatable(ProgramHolder.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Zero {
        String value() default EMPTY;
        One[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface One {
        String value() default EMPTY;
        Two[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Two {
        String value() default EMPTY;
        Three[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Three {
        String value() default EMPTY;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ProgramHolder {
        Zero[] value();
    }

    private Annotations() {}
}

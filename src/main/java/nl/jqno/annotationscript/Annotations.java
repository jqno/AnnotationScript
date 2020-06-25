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
        Four[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Four {
        String value() default EMPTY;
        Five[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Five {
        String value() default EMPTY;
        Six[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Six {
        String value() default EMPTY;
        Seven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Seven {
        String value() default EMPTY;
        Eight[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eight {
        String value() default EMPTY;
        Nine[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Nine {
        String value() default EMPTY;
        Ten[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Ten {
        String value() default EMPTY;
        Eleven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eleven {
        String value() default EMPTY;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ProgramHolder {
        Zero[] value();
    }

    private Annotations() {}
}

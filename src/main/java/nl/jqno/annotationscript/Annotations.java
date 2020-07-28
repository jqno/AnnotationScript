package nl.jqno.annotationscript;

import java.lang.annotation.*;

public final class Annotations {

    public static final String EMPTY = "";

    @Repeatable(ProgramHolder.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Zero {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        One[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface One {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Two[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Two {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Three[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Three {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Four[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Four {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Five[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Five {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Six[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Six {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Seven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Seven {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Eight[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eight {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Nine[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Nine {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Ten[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Ten {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Eleven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eleven {
        String comment() default EMPTY;
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ProgramHolder {
        Zero[] value();
    }

    public static final class Nothing {
        private Nothing() {}
    }

    private Annotations() {}
}

package nl.jqno.annotationscript;

import java.lang.annotation.*;

public final class Annotations {

    public static final String EMPTY = "";

    @Repeatable(ProgramHolder.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Zero {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        One[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface One {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Two[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Two {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Three[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Three {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Four[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Four {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Five[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Five {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Six[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Six {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Seven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Seven {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Eight[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eight {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Nine[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Nine {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Ten[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Ten {
        String value() default EMPTY;
        Class<?> include() default Nothing.class;
        Eleven[] list() default {};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Eleven {
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

package nl.jqno.annotationscript;

import nl.jqno.annotationscript.Annotations.*;

public class AnnotationsTest {

    @One(literal="begin")
    @One(list={@Two(literal="define"), @Two(literal="r"), @Two(literal="10")})
    @One(list={
        @Two(literal="*"),
        @Two(literal="pi"),
        @Two(list={@Three(literal="*"), @Three(literal="r"), @Three(literal="r")})})
    public static class Holder {}
}

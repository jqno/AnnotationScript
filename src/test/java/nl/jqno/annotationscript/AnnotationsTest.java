package nl.jqno.annotationscript;

import nl.jqno.annotationscript.Annotations.*;

public class AnnotationsTest {

    @Zero(literal="begin")
    @Zero(list={@One(literal="define"), @One(literal="r"), @One(literal="10")})
    @Zero(list={
        @One(literal="*"),
        @One(literal="pi"),
        @One(list={@Two(literal="*"), @Two(literal="r"), @Two(literal="r")})})
    public static class Holder {}
}

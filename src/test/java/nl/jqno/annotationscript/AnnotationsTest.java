package nl.jqno.annotationscript;

import nl.jqno.annotationscript.Annotations.*;

public class AnnotationsTest {

    @Zero("begin")
    @Zero(list={@One("define"), @One("r"), @One("10")})
    @Zero(list={
        @One("*"),
        @One("pi"),
        @One(list={@Two("*"), @Two("r"), @Two("r")})})
    public static class Holder {}
}

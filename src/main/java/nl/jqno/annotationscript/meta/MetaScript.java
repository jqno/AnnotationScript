package nl.jqno.annotationscript.meta;

import io.vavr.collection.HashMap;
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

public class MetaScript {

    @Zero("begin")
    @Zero(include=Helpers.class)
    @Zero(include=StringTokenizer.class)
    @Zero(include=Parser.class)
    @Zero(include=Table.class)
    @Zero(include=Evaluator.class)
    @Zero(list={
        @One("define"),
        @One("run"),
        @One(list={
            @Two("lambda"),
            @Two(list={@Three("input")}),
            @Two(list={
                @Three("value"),
                @Three(list={
                    @Four("parse"),
                    @Four(list={@Five("tokenize"), @Five("input")})})})})})
    @Zero(list={@One("run"), @One("input")})
    private static class Runner {}

    public static Object run(String source) {
        return AnnotationScript.run(Runner.class, HashMap.of("input", source));
    }
}

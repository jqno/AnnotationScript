package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

public class Evaluator {

    /*
     * Generated from:
     *
     * (define expression-to-action
     *   (lambda (e)
     *     (cond
     *       (atom? e)
     *       (atom-to-action e)
     *       else
     *       (list-to-action e))))
     */
    @Zero("define")
    @Zero("expression-to-action")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("atom?"), @Three("e")}),
            @Two(list={@Three("atom-to-action"), @Three("e")}),
            @Two("else"),
            @Two(list={@Three("list-to-action"), @Three("e")})})})
    public static class ExpressionToAction {}
}

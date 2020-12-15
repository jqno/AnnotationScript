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

    /*
     * Generated from:
     *
     * (define atom-to-action
     *   (lambda (e)
     *     (cond
     *       (number? e) *const
     *       (= e #t) *const
     *       (= e #f) *const
     *       (= e (quote cons)) *const
     *       (= e (quote car)) *const
     *       (= e (quote cdr)) *const
     *       (= e (quote null?)) *const
     *       (= e (quote eq?)) *const
     *       (= e (quote atom?)) *const
     *       (= e (quote zero?)) *const
     *       (= e (quote add1)) *const
     *       (= e (quote sub1)) *const
     *       (= e (quote number?)) *const
     *       else *identifier)))
     */
    @Zero("define")
    @Zero("atom-to-action")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("number?"), @Three("e")}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("#t")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("#f")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("cons")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("car")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("cdr")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("null?")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("eq?")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("atom?")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("zero?")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("add1")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("sub1")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("number?")})}),
            @Two("*const"),
            @Two("else"),
            @Two("*identifier")})})
    public static class AtomToAction {}

    /*
     * Generated from:
     *
     * (define list-to-action
     *   (lambda (e)
     *     (cond
     *       (atom? (head e))
     *       (cond
     *         (= (head e) (quote quote)) *quote
     *         (= (head e) (quote lambda)) *lambda
     *         (= (head e) (quote cond)) *cond
     *         else *application)
     *       else *application)))
     */
    @Zero("define")
    @Zero("list-to-action")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("atom?"), @Three(list={@Four("head"), @Four("e")})}),
            @Two(list={
                @Three("cond"),
                @Three(list={
                    @Four("="),
                    @Four(list={@Five("head"), @Five("e")}),
                    @Four(list={@Five("quote"), @Five("quote")})}),
                @Three("*quote"),
                @Three(list={
                    @Four("="),
                    @Four(list={@Five("head"), @Five("e")}),
                    @Four(list={@Five("quote"), @Five("lambda")})}),
                @Three("*lambda"),
                @Three(list={
                    @Four("="),
                    @Four(list={@Five("head"), @Five("e")}),
                    @Four(list={@Five("quote"), @Five("cond")})}),
                @Three("*cond"),
                @Three("else"),
                @Three("*application")}),
            @Two("else"),
            @Two("*application")})})
    public static class ListToAction {}

    /*
     * Generated from:
     *
     * (define value
     *   (lambda (e)
     *     (meaning e (list))))
     */
    @Zero("define")
    @Zero("value")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e")}),
        @One(list={@Two("meaning"), @Two("e"), @Two(list={@Three("list")})})})
    public static class Value {}
}

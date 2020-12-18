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

    /*
     * Generated from:
     *
     * (define meaning
     *   (lambda (e table)
     *     ((expression-to-action e) e table)))
     */
    @Zero("define")
    @Zero("meaning")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two(list={
                @Three("expression-to-action"), @Three("e")}),
            @Two("e"),
            @Two("table")})})
    public static class Meaning {}

    /*
     * Generated from:
     *
     * (define *const
     *   (lambda (e table)
     *     (cond
     *       (number? e) e
     *       (= e #t) #t
     *       (= e #f) #f
     *       else
     *         (build (quote primitive) e))))
     */
    @Zero("define")
    @Zero("*const")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("number?"), @Three("e")}),
            @Two("e"),
            @Two(list={@Three("="), @Three("e"), @Three("#t")}),
            @Two("#t"),
            @Two(list={@Three("="), @Three("e"), @Three("#f")}),
            @Two("#f"),
            @Two("else"),
            @Two(list={
                @Three("build"),
                @Three(list={@Four("quote"), @Four("primitive")}),
                @Three("e")})})})
    public static class TypeConst {}

    /*
     * Generated from:
     *
     * (define *quote
     *   (lambda (e table)
     *     (text-of e)))
     */
    @Zero("define")
    @Zero("*quote")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={@Two("text-of"), @Two("e")})})
    public static class TypeQuote {}

    /*
     * Generated from:
     *
     * (define text-of second)
     */
    @Zero("define")
    @Zero("text-of")
    @Zero("second")
    public static class TextOf {}

    /*
     * Generated from:
     *
     * (define *identifier
     *   (lambda (e table)
     *     (lookup-in-table e table initial-table)))
     */
    @Zero("define")
    @Zero("*identifier")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two("lookup-in-table"),
            @Two("e"),
            @Two("table"),
            @Two("initial-table")})})
    public static class TypeIdentifier {}

    /* 
     * Generated from:
     *
     * (define initial-table
     *   (lambda (name)
     *     (head (list))))
     */
    @Zero("define")
    @Zero("initial-table")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("name")}),
        @One(list={@Two("head"), @Two(list={@Three("list")})})})
    public static class InitialTable {}

    /*
     * Generated from:
     *
     * (define *lambda
     *   (lambda (e table)
     *     (build (quote non-primitive)
     *       (cons table (tail e))))) 
     */
    @Zero("define")
    @Zero("*lambda")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two("build"),
            @Two(list={@Three("quote"), @Three("non-primitive")}),
            @Two(list={
                @Three("cons"),
                @Three("table"),
                @Three(list={@Four("tail"), @Four("e")})})})})
    public static class TypeLambda {}

    /*
     * Generated from:
     *
     * (define table-of first)
     */
    @Zero("define")
    @Zero("table-of")
    @Zero("first")
    public static class TableOf {}

    /*
     * Generated from:
     *
     * (define formals-of second)
     */
    @Zero("define")
    @Zero("formals-of")
    @Zero("second")
    public static class FormalsOf {}

    /*
     * Generated from:
     *
     * (define body-of third)
     */
    @Zero("define")
    @Zero("body-of")
    @Zero("third")
    public static class BodyOf {}

    /*
     * Generated from:
     *
     * (define evcon
     *   (lambda (lines table)
     *     (cond
     *       (else? (question-of (head lines)))
     *       (meaning (answer-of (head lines)) table)
     *       (meaning (question-of (head lines)) table)
     *       (meaning (answer-of (head lines)) table)
     *       else (evcon (tail lines) table))))
     */
    @Zero("define")
    @Zero("evcon")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("lines"), @Two("table")}),
        @One(list={
            @Two("cond"),
            @Two(list={
                @Three("else?"),
                @Three(list={
                    @Four("question-of"),
                    @Four(list={@Five("head"), @Five("lines")})})}),
            @Two(list={
                @Three("meaning"),
                @Three(list={
                    @Four("answer-of"),
                    @Four(list={@Five("head"), @Five("lines")})}),
                @Three("table")}),
            @Two(list={
                @Three("meaning"),
                @Three(list={
                    @Four("question-of"),
                    @Four(list={@Five("head"), @Five("lines")})}),
                @Three("table")}),
            @Two(list={
                @Three("meaning"),
                @Three(list={
                    @Four("answer-of"),
                    @Four(list={@Five("head"), @Five("lines")})}),
                @Three("table")}),
            @Two("else"),
            @Two(list={
                @Three("evcon"),
                @Three(list={@Four("tail"), @Four("lines")}),
                @Three("table")})})})
    public static class Evcon {}

    /*
     * Generated from:
     *
     * (define else?
     *   (lambda (x)
     *     (cond
     *       (atom? x) (= x (quote else))
     *       else #f)))
     */
    @Zero("define")
    @Zero("else?")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("x")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("atom?"), @Three("x")}),
            @Two(list={@Three("="), @Three("x"), @Three(list={@Four("quote"), @Four("else")})}),
            @Two("else"),
            @Two("#f")})})
    public static class Else {}

    /*
     * Generated from:
     *
     * (define question-of first)
     */
    @Zero("define")
    @Zero("question-of")
    @Zero("first")
    public static class QuestionOf {}

    /*
     * Generated from:
     *
     * (define question-of first)
     */
    @Zero("define")
    @Zero("answer-of")
    @Zero("second")
    public static class AnswerOf {}

    /*
     * Generated from:
     *
     * (define *cond
     *   (lambda (e table)
     *     (evcon (cond-lines-of e) table)))
     */
    @Zero("define")
    @Zero("*cond")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two("evcon"),
            @Two(list={@Three("cond-lines-of"), @Three("e")}),
            @Two("table")})})
    public static class TypeCond {}

    /*
     * Generated from:
     *
     * (define cond-lines-of tail)
     */
    @Zero("define")
    @Zero("cond-lines-of")
    @Zero("tail")
    public static class CondLinesOf {}
}

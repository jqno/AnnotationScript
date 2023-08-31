package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

@Zero(export={
    Evaluator.ExpressionToAction.class,
    Evaluator.AtomToAction.class,
    Evaluator.ListToAction.class,
    Evaluator.Value.class,
    Evaluator.Meaning.class,
    Evaluator.TypeConst.class,
    Evaluator.TypeQuote.class,
    Evaluator.TextOf.class,
    Evaluator.TypeIdentifier.class,
    Evaluator.InitialTable.class,
    Evaluator.TypeLambda.class,
    Evaluator.TableOf.class,
    Evaluator.FormalsOf.class,
    Evaluator.BodyOf.class,
    Evaluator.Evcon.class,
    Evaluator.Else.class,
    Evaluator.QuestionOf.class,
    Evaluator.AnswerOf.class,
    Evaluator.TypeCond.class,
    Evaluator.CondLinesOf.class,
    Evaluator.TypeDefine.class,
    Evaluator.Evlis.class,
    Evaluator.TypeApplication.class,
    Evaluator.FunctionOf.class,
    Evaluator.ArgumentsOf.class,
    Evaluator.Primitive.class,
    Evaluator.NonPrimitive.class,
    Evaluator.Apply.class,
    Evaluator.ApplyPrimitive.class,
    Evaluator.Atom.class,
    Evaluator.ApplyClosure.class})
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
     *       (= e (quote mod)) *const
     *       (= e (quote +)) *const
     *       (= e (quote -)) *const
     *       (= e (quote <)) *const
     *       (= e (quote >)) *const
     *       (= e (quote nth!)) *const
     *       (= e (quote update-nth!)) *const
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
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("mod")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("+")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("-")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("<")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four(">")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("nth!")})}),
            @Two("*const"),
            @Two(list={@Three("="), @Three("e"), @Three(list={@Four("quote"), @Four("update-nth!")})}),
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
     *         (= (head e) (quote define)) *define
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
                @Three(list={
                    @Four("="),
                    @Four(list={@Five("head"), @Five("e")}),
                    @Four(list={@Five("quote"), @Five("define")})}),
                @Three("*define"),
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
            @Two(list={@Three("expression-to-action"), @Three("e")}),
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

    /*
     * Generated from:
     *
     * (define *define
     *   (lambda (e table)
     *     (meaning
     *       (third e)
     *       (extend-table
     *         (new-entry
     *           (list (first (second e)))
     *           (list (meaning (second (second e)))))
     *         table))))
     */
    @Zero("define")
    @Zero("*define")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two("meaning"),
            @Two(list={@Three("third"), @Three("e")}),
            @Two(list={
                @Three("extend-table"),
                @Three(list={
                    @Four("new-entry"),
                    @Four(list={
                        @Five("list"),
                        @Five(list={@Six("first"), @Six(list={@Seven("second"), @Seven("e")})})}),
                    @Four(list={
                        @Five("list"),
                        @Five(list={
                            @Six("meaning"),
                            @Six(list={
                                @Seven("second"),
                                @Seven(list={@Eight("second"), @Eight("e")})})})})}),
                @Three("table")})})})
    public static class TypeDefine {}

    /*
     * Generated from:
     *
     * (define evlis
     *   (lambda (args table)
     *     (cond
     *       (empty? args) (list)
     *       else
     *         (cons (meaning (head args) table)
     *               (evlis (tail args) table)))))
     */
    @Zero("define")
    @Zero("evlis")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("args"), @Two("table")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("empty?"), @Three("args")}),
            @Two(list={@Three("list")}),
            @Two("else"),
            @Two(list={
                @Three("cons"),
                @Three(list={
                    @Four("meaning"),
                    @Four(list={@Five("head"), @Five("args")}),
                    @Four("table")}),
                @Three(list={
                    @Four("evlis"),
                    @Four(list={@Five("tail"), @Five("args")}),
                    @Four("table")})})})})
    public static class Evlis {}

    /*
     * Generated from:
     *
     * (define *application
     *   (lambda (e table)
     *     (:apply
     *       (meaning (function-of e) table)
     *       (evlis (arguments-of e) table))))
     */
    @Zero("define")
    @Zero("*application")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("e"), @Two("table")}),
        @One(list={
            @Two(":apply"),
            @Two(list={
                @Three("meaning"),
                @Three(list={@Four("function-of"), @Four("e")}),
                @Three("table")}),
            @Two(list={
                @Three("evlis"),
                @Three(list={@Four("arguments-of"), @Four("e")}),
                @Three("table")})})})
    public static class TypeApplication {}

    /*
     * Generated from:
     *
     * (define function-of head)
     */
    @Zero("define")
    @Zero("function-of")
    @Zero("head")
    public static class FunctionOf {}

    /*
     * Generated from:
     *
     * (define arguments-of tail)
     */
    @Zero("define")
    @Zero("arguments-of")
    @Zero("tail")
    public static class ArgumentsOf {}

    /*
     * Generated from:
     *
     * (define primitive?
     *   (lambda (l)
     *     (= (first l) (quote primitive))))
     */
    @Zero("define")
    @Zero("primitive?")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("l")}),
        @One(list={
            @Two("="),
            @Two(list={@Three("first"), @Three("l")}),
            @Two(list={@Three("quote"), @Three("primitive")})})})
    public static class Primitive {}

    /*
     * Generated from:
     *
     * (define non-primitive?
     *   (lambda (l)
     *     (= (first l) (quote non-primitive))))
     */
    @Zero("define")
    @Zero("non-primitive?")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("l")}),
        @One(list={
            @Two("="),
            @Two(list={@Three("first"), @Three("l")}),
            @Two(list={@Three("quote"), @Three("non-primitive")})})})
    public static class NonPrimitive {}

    /*
     * Generated from:
     *
     * (define :apply
     *   (lambda (fun vals)
     *     (cond
     *       (primitive? fun)
     *       (apply-primitive (second fun) vals)
     *       (non-primitive? fun)
     *       (apply-closure (second fun) vals))))
     */
    @Zero("define")
    @Zero(":apply")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("fun"), @Two("vals")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("primitive?"), @Three("fun")}),
            @Two(list={
                @Three("apply-primitive"),
                @Three(list={@Four("second"), @Four("fun")}),
                @Three("vals")}),
            @Two(list={@Three("non-primitive?"), @Three("fun")}),
            @Two(list={
                @Three("apply-closure"),
                @Three(list={@Four("second"), @Four("fun")}),
                @Three("vals")})})})
    public static class Apply {}

    /*
     * Generated from:
     *
     * (define apply-primitive
     *   (lambda (name vals)
     *     (cond
     *       (= name (quote cons))
     *       (cons (first vals) (second vals))
     *       (= name (quote car))
     *       (head (first vals))
     *       (= name (quote cdr))
     *       (tail (first vals))
     *       (= name (quote null?))
     *       (empty? (first vals))
     *       (= name (quote eq?))
     *       (= (first vals) (second vals))
     *       (= name (quote atom?))
     *       (:atom? (first vals))
     *       (= name (quote zero?))
     *       (= 0 (first vals))
     *       (= name (quote add1))
     *       (+ (first vals) 1)
     *       (= name (quote sub1))
     *       (- (first vals) 1)
     *       (= name (quote mod))
     *       (% (first vals) (second vals))
     *       (= name (quote +))
     *       (+ (first vals) (second vals))
     *       (= name (quote -))
     *       (- (first vals) (second vals))
     *       (= name (quote <))
     *       (< (first vals) (second vals))
     *       (= name (quote >))
     *       (> (first vals) (second vals))
     *       (= name (quote nth!))
     *       (nth (first vals) (second vals))
     *       (= name (quote update-nth!))
     *       (update-nth (first vals) (second vals) (third vals))
     *       (= name (quote number?))
     *       (number? (first vals)))))
     */
    @Zero("define")
    @Zero("apply-primitive")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("name"), @Two("vals")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("cons")})}), 
            @Two(list={@Three("cons"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("car")})}),
            @Two(list={@Three("head"), @Three(list={@Four("first"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("cdr")})}),
            @Two(list={@Three("tail"), @Three(list={@Four("first"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("null?")})}),
            @Two(list={@Three("empty?"), @Three(list={@Four("first"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("eq?")})}),
            @Two(list={@Three("="), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("atom?")})}),
            @Two(list={@Three(":atom?"), @Three(list={@Four("first"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("zero?")})}),
            @Two(list={@Three("="), @Three("0"), @Three(list={@Four("first"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("add1")})}),
            @Two(list={@Three("+"), @Three(list={@Four("first"), @Four("vals")}), @Three("1")}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("sub1")})}),
            @Two(list={@Three("-"), @Three(list={@Four("first"), @Four("vals")}), @Three("1")}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("mod")})}),
            @Two(list={@Three("%"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("+")})}),
            @Two(list={@Three("+"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("-")})}),
            @Two(list={@Three("-"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("<")})}),
            @Two(list={@Three("<"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four(">")})}),
            @Two(list={@Three(">"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("nth!")})}),
            @Two(list={@Three("nth"), @Three(list={@Four("first"), @Four("vals")}), @Three(list={@Four("second"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("update-nth!")})}),
            @Two(list={
                @Three("update-nth"),
                @Three(list={@Four("first"), @Four("vals")}),
                @Three(list={@Four("second"), @Four("vals")}),
                @Three(list={@Four("third"), @Four("vals")})}),
            @Two(list={@Three("="), @Three("name"), @Three(list={@Four("quote"), @Four("number?")})}),
            @Two(list={@Three("number?"), @Three(list={@Four("first"), @Four("vals")})})})})
    public static class ApplyPrimitive {}

    /*
     * Generated from:
     *
     * (define :atom?
     *   (lambda (x)
     *     (cond
     *       (atom? x) #t
     *       (empty? x) #f
     *       (= (head x) (quote primitive)) #t
     *       (= (head x) (quote non-primitive)) #t
     *       else #f)))
     */
    @Zero("define")
    @Zero(":atom?")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("x")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("atom?"), @Three("x")}),
            @Two("#t"),
            @Two(list={@Three("empty?"), @Three("x")}),
            @Two("#f"),
            @Two(list={
                @Three("="),
                @Three(list={@Four("head"), @Four("x")}),
                @Three(list={@Four("quote"), @Four("primitive")})}),
            @Two("#t"),
            @Two(list={
                @Three("="),
                @Three(list={@Four("head"), @Four("x")}),
                @Three(list={@Four("quote"), @Four("non-primitive")})}),
            @Two("#t"),
            @Two("else"),
            @Two("#f")})})
    public static class Atom {}

    /*
     * Generated from:
     *
     * (define apply-closure
     *   (lambda (closure vals)
     *     (meaning
     *       (body-of closure)
     *       (extend-table
     *         (new-entry (formals-of closure) vals)
     *         (table-of closure)))))
     */
    @Zero("define")
    @Zero("apply-closure")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("closure"), @Two("vals")}),
        @One(list={
            @Two("meaning"),
            @Two(list={@Three("body-of"), @Three("closure")}),
            @Two(list={
                @Three("extend-table"),
                @Three(list={
                    @Four("new-entry"),
                    @Four(list={@Five("formals-of"), @Five("closure")}),
                    @Four("vals")}),
                @Three(list={@Four("table-of"), @Four("closure")})})})})
    public static class ApplyClosure {}
}

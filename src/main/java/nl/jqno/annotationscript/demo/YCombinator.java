//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo;

import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.Eight;
import nl.jqno.annotationscript.Annotations.Five;
import nl.jqno.annotationscript.Annotations.Four;
import nl.jqno.annotationscript.Annotations.One;
import nl.jqno.annotationscript.Annotations.Seven;
import nl.jqno.annotationscript.Annotations.Six;
import nl.jqno.annotationscript.Annotations.Three;
import nl.jqno.annotationscript.Annotations.Two;
import nl.jqno.annotationscript.Annotations.Zero;

/*
 * Generated from:
 *
 * (begin
 *   (define Y
 *     (lambda (le)
 *       ((lambda (f) (f f))
 *        (lambda (f)
 *          (le (lambda (x) ((f f) x)))))))
 *
 *   -- The example from The Little Schemer
 *   (define length-fn
 *     (lambda (length)
 *       (lambda (l)
 *         (cond
 *           (empty? l) 0
 *           else (+ 1 (length (tail l)))))))
 *
 *   (println ((Y length-fn) (quote (1 2 3 4 5))))
 *
 *   -- The example from the SimpleTailRecursion demo in this repo
 *   (define loop-fn
 *     (lambda (loop)
 *       (lambda (n)
 *         (if (= n 0)
 *             (quote end)
 *             (loop (- n 1))))))
 *
 *   (println ((Y loop-fn) 1000)))
 */
public class YCombinator {

    @Zero(list={
        @One("define"),
        @One("Y"),
        @One(list={
            @Two("lambda"),
            @Two(list={@Three("le")}),
            @Two(list={
                @Three(list={
                    @Four("lambda"),
                    @Four(list={@Five("f")}),
                    @Four(list={@Five("f"), @Five("f")})}),
                @Three(list={
                    @Four("lambda"),
                    @Four(list={@Five("f")}),
                    @Four(list={
                        @Five("le"),
                        @Five(list={
                            @Six("lambda"),
                            @Six(list={@Seven("x")}),
                            @Six(list={
                                @Seven(list={@Eight("f"), @Eight("f")}),
                                @Seven("x")})})})})})})})
    public static class Y {}

    @Zero("begin")
    @Zero(list={
        @One("define"),
        @One("length-fn"),
        @One(list={
            @Two("lambda"),
            @Two(list={@Three("length")}),
            @Two(list={
                @Three("lambda"),
                @Three(list={@Four("l")}),
                @Three(list={
                    @Four("cond"),
                    @Four(list={@Five("empty?"), @Five("l")}),
                    @Four("0"),
                    @Four("else"),
                    @Four(list={
                        @Five("+"),
                        @Five("1"),
                        @Five(list={@Six("length"), @Six(list={@Seven("tail"), @Seven("l")})})})})})})})
    @Zero(list={
        @One("println"),
        @One(list={
            @Two(list={@Three("Y"), @Three("length-fn")}),
            @Two(list={
                @Three("quote"),
                @Three(list={@Four("1"), @Four("2"), @Four("3"), @Four("4"), @Four("5")})})})})
    public static class TheLittleSchemerExample {}

    @Zero("begin")
    @Zero(list={
        @One("define"),
        @One("loop-fn"),
        @One(list={
            @Two("lambda"),
            @Two(list={@Three("loop")}),
            @Two(list={
                @Three("lambda"),
                @Three(list={@Four("n")}),
                @Three(list={
                    @Four("if"),
                    @Four(list={@Five("="), @Five("n"), @Five("0")}),
                    @Four(list={@Five("quote"), @Five("end")}),
                    @Four(list={@Five("loop"), @Five(list={@Six("-"), @Six("n"), @Six("1")})})})})})})
    @Zero(list={
        @One("println"),
        @One(list={
            @Two(list={@Three("Y"), @Three("loop-fn")}),
            @Two("1000")})})
    public static class SimpleTailRecursionExample {}

    @Zero("begin")
    @Zero(include=Y.class)
    @Zero(include=TheLittleSchemerExample.class)
    @Zero(include=SimpleTailRecursionExample.class)
    public static class YCombinatorExample {}

    public static void main(String...args) {
        AnnotationScript.run(YCombinatorExample.class);
    }
}

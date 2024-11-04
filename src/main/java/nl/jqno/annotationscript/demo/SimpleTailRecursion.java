package nl.jqno.annotationscript.demo;

import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.Five;
import nl.jqno.annotationscript.Annotations.Four;
import nl.jqno.annotationscript.Annotations.One;
import nl.jqno.annotationscript.Annotations.Three;
import nl.jqno.annotationscript.Annotations.Two;
import nl.jqno.annotationscript.Annotations.Zero;

// Without tail call optimization, this demo throws a StackOverflowError after ~900 iterations on my machine.
// With tail call optimization, it can run arbitrarily long. Try it!

/*
 * Generated from:
 *
 * (begin
 *   (define loop
 *     (lambda (n count)
 *       (if
 *         (= count n)
 *         n
 *         (loop n (+ count 1)))))
 *   (println (loop 1000 0)))
 */
@Zero("begin")
@Zero(list={
    @One("define"),
    @One("loop"),
    @One(list={
        @Two("lambda"),
        @Two(list={@Three("n"), @Three("count")}),
        @Two(list={
            @Three("if"),
            @Three(list={@Four("="), @Four("count"), @Four("n")}),
            @Three("n"),
            @Three(list={
                @Four("loop"),
                @Four("n"),
                @Four(list={@Five("+"), @Five("count"), @Five("1")})})})})})
@Zero(list={@One("println"), @One(list={@Two("loop"), @Two("1000"), @Two("0")})})
public class SimpleTailRecursion {
    public static void main(String[] args) {
        AnnotationScript.run(SimpleTailRecursion.class);
    }
}

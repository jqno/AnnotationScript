//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo;

import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

/*
 * Generated from:
 *
 * (begin
 *   (define fizz-buzz
 *     (lambda (n)
 *       (cond
 *         ((= (% n 15) 0) 'fizzbuzz')
 *         ((= (% n 3) 0) 'fizz')
 *         ((= (% n 5) 0) 'buzz')
 *         (else n))))
 *   (map println (map fizz-buzz (range 1 101))))
 */
@Zero("begin")
@Zero(list={@One("define"), @One("fizz-buzz"), @One(list={@Two("lambda"), @Two(list=@Three("n")), @Two(list={
    @Three("cond"),
    @Three(list={@Four("="), @Four(list={@Five("%"), @Five("n"), @Five("15")}), @Four("0")}), @Three("'fizzbuzz'"),
    @Three(list={@Four("="), @Four(list={@Five("%"), @Five("n"), @Five("3")}), @Four("0")}), @Three("'fizz'"),
    @Three(list={@Four("="), @Four(list={@Five("%"), @Five("n"), @Five("5")}), @Four("0")}), @Three("'buzz'"),
    @Three("else"), @Three("n")})})})
@Zero(list={@One("map"), @One("println"), @One(list={@Two("map"), @Two("fizz-buzz"), @Two(list={@Three("range"), @Three("1"), @Three("101")})})})
public class FizzBuzz {
    public static void main(String[] args) {
        AnnotationScript.run(FizzBuzz.class);
    }
}

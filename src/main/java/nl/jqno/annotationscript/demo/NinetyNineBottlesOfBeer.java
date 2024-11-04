package nl.jqno.annotationscript.demo;

import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

@Zero(comment="Based on http://www.99-bottles-of-beer.net/language-clojure-2328.html")
public class NinetyNineBottlesOfBeer {

    /*
     * Generated from:
     *
     * (define bottles-str
     *   (lambda (n)
     *     (str/concat
     *       (cond
     *         ((= 0 n) 'no more bottles')
     *         ((= 1 n) '1 bottle')
     *         (else (str/concat n ' bottles')))
     *       ' of beer')))
     */
    @Zero("define")
    @Zero("bottles-str")
    @Zero(list={@One("lambda"), @One(list=@Two("n")), @One(list={@Two("str/concat"),
        @Two(list={@Three("cond"),
            @Three(list={@Four("="), @Four("0"), @Four("n")}), @Three("'no more bottles'"),
            @Three(list={@Four("="), @Four("1"), @Four("n")}), @Three("'1 bottle'"),
            @Three("else"), @Three(list={@Four("str/concat"), @Four("n"), @Four("' bottles'")})}),
        @Two("' of beer'")})})
    public static class Bottles {}

    /*
     * Generated from:
     *
     * (define print-bottle
     *   (lambda (n)
     *     (begin
     *       (println
     *         (str/concat
     *           (bottles-str n)
     *           " on the wall, "
     *           (bottles-str n)
     *           "."))
     *       (println
     *         (str/concat
     *           "Take one down and pass it around, "
     *           (bottles-str (dec n))
     *           " on the wall."))
     *       (println))))
     */
    @Zero("define")
    @Zero("print-bottle")
    @Zero(list={@One("lambda"), @One(list=@Two("n")), @One(list={
        @Two("begin"),
        @Two(list={@Three("println"), @Three(list={@Four("str/concat"),
            @Four(list={@Five("bottles-str"), @Five("n")}),
            @Four("' on the wall, '"),
            @Four(list={@Five("bottles-str"), @Five("n")}),
            @Four("'.'")})}),
        @Two(list={@Three("println"), @Three(list={@Four("str/concat"),
            @Four("'Take one down and pass it around, '"),
            @Four(list={@Five("bottles-str"), @Five(list={@Six("dec"), @Six("n")})}),
            @Four("' on the wall.'")})}),
        @Two(list={@Three("println")})})})
    public static class PrintBottle {}

    /*
     * Generated from:
     *
     * (define sing
     *   (lambda (n)
     *     (begin
     *       (map print-bottle (reverse (range 1 (inc n))))
     *       (println "No more bottles of beer on the wall, no more bottles of beer.")
     *       (println
     *         (str/concat
     *           "Go to the store and buy some more, "
     *           (bottles-str n)
     *           " on the wall.")))))
     */
    @Zero("define")
    @Zero("sing")
    @Zero(list={@One("lambda"), @One(list=@Two("n")), @One(list={
        @Two("begin"),
        @Two(list={@Three("map"),
            @Three("print-bottle"),
            @Three(list={@Four("reverse"), @Four(list={@Five("range"), @Five("1"), @Five(list={@Six("inc"), @Six("n")})})})}),
        @Two(list={@Three("println"), @Three("'No more bottles of beer on the wall, no more bottles of beer.'")}),
        @Two(list={@Three("println"), @Three(list={@Four("str/concat"),
            @Four("'Go to the store and buy some more, '"),
            @Four(list={@Five("bottles-str"), @Five("n")}),
            @Four("' on the wall.'")})})})})
    public static class Sing {}

    /*
     * Generated from:
     *
     * (begin
     *   (include Bottles.class)
     *   (include PrintBottle.class)
     *   (include Sing.class)
     *   (sing 99))
     */
    @Zero("begin")
    @Zero(include=Bottles.class)
    @Zero(include=PrintBottle.class)
    @Zero(include=Sing.class)
    @Zero(list={@One("sing"), @One("99")})
    public static class Main {}

    public static void main(String[] args) {
        AnnotationScript.run(Main.class);
    }
}

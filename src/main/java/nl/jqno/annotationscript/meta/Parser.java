package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

/*
 * Generated from:
 *
 */
public class Parser {
    /*
     * Generated from:
     *
     * (define second (lambda (l) (head (tail l)))) 
     */
    @Zero("define")
    @Zero("second")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("l")}),
        @One(list={@Two("head"), @Two(list={@Three("tail"), @Three("l")})})})
    public static class Second {}

    /*
     * Generated from:
     *
     *  (define read-atom
     *    (lambda (token)
     *      (cond
     *        (and (str/starts-with? ''' token) (str/ends-with? ''' token))
     *        (str/substring token 1 (- (str/length token) 1))
     *        (not (null? (parse-int token)))
     *        (parse-int token)
     *        (not (null? (parse-float token)))
     *        (parse-float token)
     *        else
     *        (symbol token))))
     */
    @Zero("define")
    @Zero("read-atom")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("token")}),
        @One(list={
            @Two("cond"),
            @Two(list={
                @Three("and"),
                @Three(list={@Four("str/starts-with?"), @Four("'''"), @Four("token")}),
                @Three(list={@Four("str/ends-with?"), @Four("'''"), @Four("token")})}),
            @Two(list={
                @Three("str/substring"),
                @Three("token"),
                @Three("1"),
                @Three(list={@Four("-"), @Four(list={@Five("str/length"), @Five("token")}), @Four("1")})}),
            @Two(list={
                @Three("not"),
                @Three(list={@Four("null?"), @Four(list={@Five("parse-int"), @Five("token")})})}),
            @Two(list={@Three("parse-int"), @Three("token")}),
            @Two(list={
                @Three("not"),
                @Three(list={@Four("null?"), @Four(list={@Five("parse-float"), @Five("token")})})}),
            @Two(list={@Three("parse-float"), @Three("token")}),
            @Two("else"),
            @Two(list={@Three("symbol"), @Three("token")})})})
    public static class ReadAtom {}
}

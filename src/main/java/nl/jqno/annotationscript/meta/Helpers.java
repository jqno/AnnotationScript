package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

@Zero(export={
    Helpers.First.class,
    Helpers.Second.class,
    Helpers.Third.class})
public class Helpers {
    /*
     * Generated from:
     *
     * (define first (lambda (l) (head l)))
     */
    @Zero("define")
    @Zero("first")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("l")}),
        @One(list={@Two("head"), @Two("l")})})
    public static class First {}

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
     * (define third (lambda (l) (head (tail (tail l)))))
     */
    @Zero("define")
    @Zero("third")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("l")}),
        @One(list={@Two("head"), @Two(list={@Three("tail"), @Three(list={@Four("tail"), @Four("l")})})})})
    public static class Third {}
}

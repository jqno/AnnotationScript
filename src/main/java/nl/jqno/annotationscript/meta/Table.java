package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

public class Table {

    /*
     * Generated from:
     *
     * (define new-entry build)
     */
    @Zero("define")
    @Zero("new-entry")
    @Zero("build")
    public static class NewEntry {}

    /*
     * Generated from:
     *
     * (define lookup-in-entry
     *   (lambda (name entry entry-f)
     *     (lookup-in-entry-help
     *       name
     *       (first entry)
     *       (second entry)
     *       entry-f)))
     */
    @Zero("define")
    @Zero("lookup-in-entry")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("name"), @Two("entry"), @Two("entry-f")}),
        @One(list={
            @Two("lookup-in-entry-help"),
            @Two("name"),
            @Two(list={@Three("first"), @Three("entry")}),
            @Two(list={@Three("second"), @Three("entry")}),
            @Two("entry-f")})})
    public static class LookupInEntry {}

    /*
     * Generated from:
     *
     * (define lookup-in-entry-help
     *   (lambda (name names values entry-f)
     *     (cond
     *       (empty? names)
     *       (entry-f name)
     *       (= (head names) name)
     *       (head values)
     *       else
     *       (lookup-in-entry-help
     *         name
     *         (tail names)
     *         (tail values)
     *         entry-f))))
     */
    @Zero("define")
    @Zero("lookup-in-entry-help")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("name"), @Two("names"), @Two("values"), @Two("entry-f")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("empty?"), @Three("names")}),
            @Two(list={@Three("entry-f"), @Three("name")}),
            @Two(list={@Three("="), @Three(list={@Four("head"), @Four("names")}), @Three("name")}),
            @Two(list={@Three("head"), @Three("values")}),
            @Two("else"),
            @Two(list={
                @Three("lookup-in-entry-help"),
                @Three("name"),
                @Three(list={@Four("tail"), @Four("names")}),
                @Three(list={@Four("tail"), @Four("values")}),
                @Three("entry-f")})})})
    public static class LookupInEntryHelp {}
}

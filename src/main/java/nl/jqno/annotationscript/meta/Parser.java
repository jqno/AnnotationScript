package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

@Zero("define")
@Zero("parse")
@Zero(list={
    @One("lambda"),
    @One(list={@Two("all-tokens")}),
    @One(list={
        @Two("begin"),
        @Two(include=Helpers.class),
        @Two(include=Parser.ReadFromTokens.class),
        @Two(include=Parser.ReadList.class),
        @Two(include=Parser.ReadAtom.class),
        @Two(include=Parser.InnerParse.class),
        @Two(list={@Three("inner-parse"), @Three("all-tokens")})})})
public class Parser {
    /*
     * Generated from:
     *
     * (define read-from-tokens
     *   (lambda (tokens)
     *     (begin
     *       (define token (first tokens))
     *       (cond
     *         (= '(' token)
     *         (read-list (list) (tail tokens))
     *         (= ')' token)
     *         (throw 'unexpected )')
     *         else
     *         (list (read-atom token) (tail tokens))))))
     */
    @Zero("define")
    @Zero("read-from-tokens")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("tokens")}),
        @One(list={
            @Two("begin"),
            @Two(list={
                @Three("define"),
                @Three("token"),
                @Three(list={@Four("first"), @Four("tokens")})}),
            @Two(list={
                @Three("cond"),
                @Three(list={@Four("="), @Four("'('"), @Four("token")}),
                @Three(list={
                    @Four("read-list"),
                    @Four(list={@Five("list")}),
                    @Four(list={@Five("tail"), @Five("tokens")})}),
                @Three(list={@Four("="), @Four("')'"), @Four("token")}),
                @Three(list={@Four("throw"), @Four("'unexpected )'")}),
                @Three("else"),
                @Three(list={
                    @Four("list"),
                    @Four(list={@Five("read-atom"), @Five("token")}),
                    @Four(list={@Five("tail"), @Five("tokens")})})})})})
    public static class ReadFromTokens {}

    /*
     * Generated from:
     *
     * (define read-list
     *   (lambda (accumulated tokens)
     *     (cond
     *       (empty? tokens)
     *       (throw 'unexpected EOF')
     *       (= ')' (first tokens))
     *       (list accumulated (tail tokens))
     *       else
     *       (begin
     *         (define parsed (read-from-tokens tokens))
     *         (read-list (append (first parsed) accumulated) (second parsed))))))
     */
    @Zero("define")
    @Zero("read-list")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("accumulated"), @Two("tokens")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("empty?"), @Three("tokens")}),
            @Two(list={@Three("throw"), @Three("'unexpected EOF'")}),
            @Two(list={@Three("="), @Three("')'"), @Three(list={@Four("first"), @Four("tokens")})}),
            @Two(list={
                @Three("list"),
                @Three("accumulated"),
                @Three(list={@Four("tail"), @Four("tokens")})}),
            @Two("else"),
            @Two(list={
                @Three("begin"),
                @Three(list={
                    @Four("define"),
                    @Four("parsed"),
                    @Four(list={@Five("read-from-tokens"), @Five("tokens")})}),
                @Three(list={
                    @Four("read-list"),
                    @Four(list={
                        @Five("append"),
                        @Five(list={@Six("first"), @Six("parsed")}),
                        @Five("accumulated")}),
                    @Four(list={@Five("second"), @Five("parsed")})})})})})
    public static class ReadList {}

    /*
     * Generated from:
     *
     * (define read-atom
     *   (lambda (token)
     *     (cond
     *       (and (str/starts-with? ''' token) (str/ends-with? ''' token))
     *       (str/substring token 1 (- (str/length token) 1))
     *       (not (null? (parse-int token)))
     *       (parse-int token)
     *       (not (null? (parse-float token)))
     *       (parse-float token)
     *       else
     *       (symbol token))))
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

    /*
     * Generated from:
     *
     * (define inner-parse
     *   (lambda (all-tokens)
     *     (cond
     *       (empty? all-tokens)
     *       (throw 'no input')
     *       else
     *       (begin
     *         (define parsed (read-from-tokens all-tokens))
     *         (cond
     *           (not (empty? (second parsed)))
     *           (throw 'unexpected end of program')
     *           else
     *           (first parsed))))))
     */
    @Zero("define")
    @Zero("inner-parse")
    @Zero(list={
        @One("lambda"),
        @One(list={@Two("all-tokens")}),
        @One(list={
            @Two("cond"),
            @Two(list={@Three("empty?"), @Three("all-tokens")}),
            @Two(list={@Three("throw"), @Three("'no input'")}),
            @Two("else"),
            @Two(list={
                @Three("begin"),
                @Three(list={
                    @Four("define"),
                    @Four("parsed"),
                    @Four(list={@Five("read-from-tokens"), @Five("all-tokens")})}),
                @Three(list={
                    @Four("cond"),
                    @Four(list={
                        @Five("not"),
                        @Five(list={
                            @Six("empty?"),
                            @Six(list={@Seven("second"), @Seven("parsed")})})}),
                    @Four(list={@Five("throw"), @Five("'unexpected end of program'")}),
                    @Four("else"),
                    @Four(list={@Five("first"), @Five("parsed")})})})})})
    public static class InnerParse {}
}

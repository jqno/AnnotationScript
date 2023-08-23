package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

// CHECKSTYLE OFF: AvoidEscapedUnicodeCharacters

/*
 * Generated from:
 *
 * (define tokenize (lambda (input)
 *   (begin (define PUNCTUATION_SPACE '\\u2008')
 *     (filter (lambda (s) (not (= s '')))
 *       (map (lambda (s) (str/replace s PUNCTUATION_SPACE '\\ '))
 *         (str/split '\\ '
 *           (str/replace
 *             (str/replace
 *               (str/replace
 *                 (str/replace input '\\\\\\ ' PUNCTUATION_SPACE)
 *               '\\n' '\\ ')
 *             '[' '\\ [\\ ')
 *           ']' '\\ ]\\ ')))))))
 *
 * NOTE: After generating, replace [ and ] literals above with ( and ) literals.
 *       The tokenizer can't deal with ( and ) directly...😅
 */
@Zero("define")
@Zero("tokenize")
@Zero(list={
    @One("lambda"),
    @One(list={@Two("input")}),
    @One(list={
        @Two("begin"),
        @Two(list={@Three("define"), @Three("PUNCTUATION_SPACE"), @Three("'\u2008'")}),
        @Two(list={
            @Three("filter"),
            @Three(list={
                @Four("lambda"),
                @Four(list={@Five("s")}),
                @Four(list={@Five("not"), @Five(list={@Six("="), @Six("s"), @Six("''")})})}),
            @Three(list={
                @Four("map"),
                @Four(list={
                    @Five("lambda"),
                    @Five(list={@Six("s")}),
                    @Five(list={@Six("str/replace"), @Six("s"), @Six("PUNCTUATION_SPACE"), @Six("' '")})}),
                @Four(list={
                    @Five("str/split"), @Five("' '"), @Five(list={
                        @Six("str/replace"), @Six(list={
                            @Seven("str/replace"), @Seven(list={
                                @Eight("str/replace"), @Eight(list={
                                    @Nine("str/replace"), @Nine("input"), @Nine("'\\ '"), @Nine("PUNCTUATION_SPACE")}),
                                @Eight("'\n'"), @Eight("' '")}),
                            @Seven("'('"), @Seven("' ( '")}),
                        @Six("')'"), @Six("' ) '")})})})})})})
public class StringTokenizer {}

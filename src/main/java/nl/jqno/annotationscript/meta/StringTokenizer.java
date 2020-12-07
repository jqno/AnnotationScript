package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

@Zero("begin")
@Zero(list={@One("define"), @One("PUNCTUATION_SPACE"), @One("'\u2008'")})
@Zero(list={
    @One("filter"),
    @One(list={
        @Two("lambda"),
        @Two(list={@Three("s")}),
        @Two(list={@Three("not"), @Three(list={@Four("="), @Four("s"), @Four("''")})})}),
    @One(list={
        @Two("map"),
        @Two(list={
            @Three("lambda"),
            @Three(list={@Four("s")}),
            @Three(list={@Four("str/replace"), @Four("s"), @Four("PUNCTUATION_SPACE"), @Four("' '")})}),
        @Two(list={
            @Three("str/split"), @Three("' '"), @Three(list={
                @Four("str/replace"), @Four(list={
                    @Five("str/replace"), @Five(list={
                        @Six("str/replace"), @Six("input"), @Six("'\\ '"), @Six("PUNCTUATION_SPACE")}),
                    @Five("'('"), @Five("' ( '")}),
                @Four("')'"), @Four("' ) '")})})})})
public class StringTokenizer {}

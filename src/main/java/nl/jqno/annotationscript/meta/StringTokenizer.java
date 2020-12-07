package nl.jqno.annotationscript.meta;

import nl.jqno.annotationscript.Annotations.*;

@Zero("filter")
@Zero(list={@One("lambda"), @One(list={@Two("s")}), @One(list={@Two("not"), @Two(list={@Three("="), @Three("s"), @Three("''")})})})
@Zero(list={@One("str/split"), @One("' '"), @One(list={
    @Two("str/replace"), @Two(list={
        @Three("str/replace"), @Three("input"), @Three("'('"), @Three("' ( '")}),
    @Two("')'"), @Two("' ) '")})})
public class StringTokenizer {}

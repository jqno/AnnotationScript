package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.fn.Value;

public class GlobalEnvironmentTest {
    private static final Environment ENV = GlobalEnvironment.build();

    @Test
    public void addition() {
        assertEquals(1.0, evaluate("+", 1.0));
        assertEquals(42, evaluate("+", 3, 33, 6));
        assertEquals(42.0, evaluate("+", 3, 33.0, 6));
        assertEquals(42.0, evaluate("+", "3", 33.0, 6));
        assertEquals(42.0, evaluate("+", new Symbol("3"), 33.0, 6));
    }

    @Test
    public void subtraction() {
        assertEquals(1.0, evaluate("-", 1.0));
        assertEquals(10, evaluate("-", 42, 30, 2));
        assertEquals(10.0, evaluate("-", 42, 30.0, 2));
        assertEquals(10.0, evaluate("-", 42, 30, "2"));
        assertEquals(10.0, evaluate("-", 42, 30, new Symbol("2")));
    }

    @Test
    public void multiplication() {
        assertEquals(1.0, evaluate("*", 1.0));
        assertEquals(24, evaluate("*", 2, 3, 4));
        assertEquals(24.0, evaluate("*", 2, 3.0, 4));
        assertEquals(24.0, evaluate("*", 2, "3.0", 4));
        assertEquals(24.0, evaluate("*", 2, new Symbol("3.0"), 4));
    }

    @Test
    public void division() {
        assertEquals(1, evaluate("/", 1));
        assertEquals(2, evaluate("/", 10, 2, 2));
        assertEquals(2.5, evaluate("/", 10, 2.0, 2));
        assertEquals(2.5, evaluate("/", "10", 2.0, 2));
        assertEquals(2.5, evaluate("/", new Symbol("10"), 2.0, 2));
    }

    @Test
    public void modulo() {
        assertEquals(1.0, evaluate("%", 1.0));
        assertEquals(2, evaluate("%", 17, 6, 3));
        assertEquals(2.0, evaluate("%", 17, 6.0, 3));
        assertEquals(2.0, evaluate("%", 17, "6.0", 3));
        assertEquals(2.0, evaluate("%", 17, new Symbol("6.0"), 3));
    }

    @Test
    public void greaterThan() {
        assertEquals(true, evaluate(">", 10, 4));
        assertEquals(false, evaluate(">", 10, 10));
        assertEquals(false, evaluate(">", 4, 10));
        assertEquals(true, evaluate(">", 10, "4"));
        assertEquals(true, evaluate(">", 10, new Symbol("4")));
    }

    @Test
    public void lessThan() {
        assertEquals(true, evaluate("<", 4, 10));
        assertEquals(false, evaluate("<", 10, 10));
        assertEquals(false, evaluate("<", 10, 4));
        assertEquals(true, evaluate("<", "4", 10));
        assertEquals(true, evaluate("<", new Symbol("4"), 10));
    }

    @Test
    public void greaterThanOrEqualTo() {
        assertEquals(true, evaluate(">=", 10, 4));
        assertEquals(true, evaluate(">=", 10, 10));
        assertEquals(false, evaluate(">=", 4, 10));
        assertEquals(true, evaluate(">=", "10", 4));
        assertEquals(true, evaluate(">=", new Symbol("10"), 4));
    }

    @Test
    public void lessThanOrEqualTo() {
        assertEquals(true, evaluate("<=", 4, 10));
        assertEquals(true, evaluate("<=", 10, 10));
        assertEquals(false, evaluate("<=", 10, 4));
        assertEquals(true, evaluate("<=", 4, "10"));
        assertEquals(true, evaluate("<=", 4, new Symbol("10")));
    }

    @Test
    public void equalTo() {
        assertEquals(true, evaluate("=", 10, 10));
        assertEquals(false, evaluate("=", 10, 4));
        assertEquals(true, evaluate("=", 0, 0.0));
        assertEquals(false, evaluate("=", new Symbol("a"), 0.0));
        assertEquals(false, evaluate("=", 0, new Symbol("b")));
        assertEquals(true, evaluate("=", new Symbol("a"), new Symbol("a")));
        assertEquals(false, evaluate("=", new Symbol("a"), new Symbol("b")));
        assertEquals(true, evaluate("=", "a", "a"));
        assertEquals(false, evaluate("=", "a", "b"));
    }

    @Test
    public void abs() {
        assertEquals(1.0, evaluate("abs", -1.0));
        assertEquals(1.0, evaluate("abs", 1.0));
        assertEquals(1, evaluate("abs", -1));
        assertEquals(1, evaluate("abs", 1));
        assertEquals(1.0, evaluate("abs", "-1"));
        assertEquals(1.0, evaluate("abs", new Symbol("-1")));
    }

    @Test
    public void and() {
        assertEquals(true, evaluate("and", 1, 42));
        assertEquals(false, evaluate("and", 0, 42));
        assertEquals(false, evaluate("and", 1, 0));
        assertEquals(false, evaluate("and", 0, 0));

        assertEquals(true, evaluate("and", 1, 42, 1, 1));
        assertEquals(false, evaluate("and", 1, 42, 0, 1));
        assertEquals(false, evaluate("and", 0, 0, 0, 0));
    }

    @Test
    public void append() {
        assertEquals(List.of(1, 2, 3, 42), evaluate("append", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("append", 42, List.empty()));
    }

    @Test
    public void apply() {
        assertEquals(42, evaluate("apply", ENV.lookup(new Symbol("+")), List.of(10, 32)));
        assertEquals(true, evaluate("apply", ENV.lookup(new Symbol("number?")), List.of(42)));
    }

    @Test
    public void atomp() {
        assertEquals(true, evaluate("atom?", true));
        assertEquals(true, evaluate("atom?", 42));
        assertEquals(true, evaluate("atom?", 42.0));
        assertEquals(true, evaluate("atom?", "string"));
        assertEquals(true, evaluate("atom?", new Symbol("symbol")));
        assertEquals(false, evaluate("atom?", List.empty()));
    }

    @Test
    public void begin() {
        assertEquals(1, evaluate("begin", 1));
        assertEquals("z", evaluate("begin", "x", "y", "z"));
        assertEquals(new Symbol("z"), evaluate("begin", new Symbol("x"), new Symbol("y"), new Symbol("z")));
    }

    @Test
    public void cons() {
        assertEquals(List.of(42, 1, 2, 3), evaluate("cons", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("cons", 42, List.empty()));
    }

    @Test
    public void containsp() {
        assertEquals(true, evaluate("contains?", List.of(1, 2, 3), 2));
        assertEquals(false, evaluate("contains?", List.of(1, 2, 3), 4));
        assertEquals(true, evaluate("contains?", List.of(1, new Symbol("two"), "three"), new Symbol("two")));
    }

    @Test
    public void dec() {
        assertEquals(42, evaluate("dec", 43));
        assertEquals(42.0, evaluate("dec", 43.0));
    }

    @Test
    public void else_() {
        assertEquals(true, evaluate("else"));
    }

    @Test
    public void emptyp() {
        assertEquals(false, evaluate("empty?", "non-empty"));
        assertEquals(true, evaluate("empty?", ""));
        assertEquals(false, evaluate("empty?", List.of(1)));
        assertEquals(true, evaluate("empty?", List.empty()));
        assertEquals(false, evaluate("empty?", HashMap.of(1, 1)));
        assertEquals(true, evaluate("empty?", HashMap.empty()));
        assertNull(evaluate("empty?", new Symbol("some-symbol")));
        assertNull(evaluate("empty?", 42));
    }

    @Test
    public void filter() {
        assertEquals(List.of(1, 3.0), evaluate("filter", ENV.lookup(new Symbol("number?")), List.of(1, "two", 3.0)));
    }

    @Test
    public void foldLeft() {
        assertEquals(42, evaluate("fold-left", ENV.lookup(new Symbol("+")), 10, List.of(8, 4, 12, 8)));
    }

    @Test
    public void head() {
        assertEquals(1, evaluate("head", List.of(1, 2, 3)));
        assertEquals(null, evaluate("head", List.empty()));
        assertEquals(null, evaluate("head"));
        assertEquals(null, evaluate("head", new Symbol("not-a-list")));
    }

    @Test
    public void inc() {
        assertEquals(42, evaluate("inc", 41));
        assertEquals(42.0, evaluate("inc", 41.0));
    }

    @Test
    public void length() {
        assertEquals(3, evaluate("length", List.of(1, 2, 3)));
        assertEquals(0, evaluate("length", List.empty()));
        assertNull(evaluate("length"));
        assertNull(evaluate("length", new Symbol("not-a-list")));
    }

    @Test
    public void list() {
        assertEquals(List.of(1, 2, 3), evaluate("list", 1, 2, 3));
        assertEquals(List.empty(), evaluate("list"));
    }

    @Test
    public void listp() {
        assertEquals(true, evaluate("list?", List.of(1, 2, 3)));
        assertEquals(true, evaluate("list?", List.empty()));
        assertEquals(false, evaluate("list?", new Symbol("not-a-list")));
        assertEquals(false, evaluate("list?", 1));
    }

    @Test
    public void map() {
        assertEquals(List.of(true, false, true), evaluate("map", ENV.lookup(new Symbol("number?")), List.of(1, new Symbol("two"), 3.0)));
    }

    @Test
    public void mapContainsp() {
        assertEquals(true, evaluate("map/contains?", HashMap.of(new Symbol("a"), 42, new Symbol("b"), 10), new Symbol("a")));
        assertEquals(false, evaluate("map/contains?", HashMap.of(new Symbol("a"), 42, new Symbol("b"), 10), new Symbol("c")));
    }

    @Test
    public void mapEmpty() {
        assertEquals(HashMap.empty(), evaluate("map/empty"));
    }

    @Test
    public void mapEntries() {
        assertEquals(List.of(List.of("a", 42), List.of("b", 10)), evaluate("map/entries", HashMap.of("a", 42, "b", 10)));
    }

    @Test
    public void mapGet() {
        assertEquals(42, evaluate("map/get", HashMap.of(new Symbol("a"), 42, new Symbol("b"), 10), new Symbol("a")));
        assertNull(evaluate("map/get", HashMap.empty(), new Symbol("a")));
    }

    @Test
    public void mapKeys() {
        assertEquals(List.of("a", "b"), evaluate("map/keys", HashMap.of("a", 42, "b", 10)));
    }

    @Test
    public void mapMerge() {
        assertEquals(HashMap.of("a", 42, "b", 10, "c", 1337), evaluate("map/merge", HashMap.of("a", 42, "b", 10), HashMap.of("a", 0, "c", 1337)));
        assertEquals(HashMap.of("a", 0, "b", 10, "c", 1337), evaluate("map/merge", HashMap.of("a", 0, "c", 1337), HashMap.of("a", 42, "b", 10)));
    }

    @Test
    public void mapOf() {
        assertEquals(HashMap.of(new Symbol("a"), 42, new Symbol("b"), 10), evaluate("map/of", new Symbol("a"), 42, new Symbol("b"), 10));
        assertEquals(HashMap.of("a", 42, "b", 10), evaluate("map/of", "a", 42, "b", 10));
    }

    @Test
    public void mapPut() {
        assertEquals(HashMap.of("a", 42, "b", 10), evaluate("map/put", HashMap.of("a", 42), "b", 10));
    }

    @Test
    public void mapRemove() {
        assertEquals(HashMap.of("a", 42), evaluate("map/remove", HashMap.of("a", 42, "b", 10), "b"));
        assertEquals(HashMap.of("a", 42), evaluate("map/remove", HashMap.of("a", 42), "b"));
    }

    @Test
    public void mapSize() {
        assertEquals(0, evaluate("map/size", HashMap.empty()));
        assertEquals(2, evaluate("map/size", HashMap.of("a", 42, "b", 10)));
    }

    @Test
    public void mapValues() {
        assertEquals(List.of(42, 10), evaluate("map/values", HashMap.of("a", 42, "b", 10)));
    }

    @Test
    public void max() {
        assertEquals(42.0, evaluate("max", 42.0));
        assertEquals(42, evaluate("max", 6, 42, 3));
        assertEquals(42.0, evaluate("max", 6, 42.0, 3));
        assertEquals(42.0, evaluate("max", 6, "42.0", 3));
        assertEquals(42.0, evaluate("max", 6, new Symbol("42.0"), 3));
    }

    @Test
    public void min() {
        assertEquals(42.0, evaluate("min", 42.0));
        assertEquals(3, evaluate("min", 6, 42, 3));
        assertEquals(3.0, evaluate("min", 6, 42.0, 3));
        assertEquals(3.0, evaluate("min", "6", 42, 3));
        assertEquals(3.0, evaluate("min", new Symbol("6"), 42, 3));
    }

    @Test
    public void not() {
        assertEquals(true, evaluate("not", 0));
        assertEquals(true, evaluate("not", 0.0));
        assertEquals(false, evaluate("not", 1));
        assertEquals(false, evaluate("not", 2));
        assertEquals(false, evaluate("not", "something"));
        assertEquals(false, evaluate("not", new Symbol("something")));
        assertEquals(false, evaluate("not", List.empty()));
    }

    @Test
    public void null_() {
        assertNull(evaluate("null"));
    }

    @Test
    public void nullp() {
        assertEquals(true, evaluate("null?", Option.none().getOrNull())); // avoid warnings caused by typing `null` directly
        assertEquals(true, evaluate("null?", evaluate("null")));
        assertEquals(false, evaluate("null?", "null"));
        assertEquals(false, evaluate("null?", 1));
    }

    @Test
    public void numberp() {
        assertEquals(true, evaluate("number?", 1));
        assertEquals(true, evaluate("number?", 1.0));
        assertEquals(false, evaluate("number?", "not-a-number"));
        assertEquals(false, evaluate("number?", new Symbol("not-a-number")));
        assertEquals(false, evaluate("number?", List.empty()));
    }

    @Test
    public void or() {
        assertEquals(true, evaluate("or", 1, 42));
        assertEquals(true, evaluate("or", 0, 42));
        assertEquals(true, evaluate("or", 1, 0));
        assertEquals(false, evaluate("or", 0, 0));

        assertEquals(true, evaluate("or", 1, 42, 1, 1));
        assertEquals(true, evaluate("or", 0, 42, 0, 0));
        assertEquals(false, evaluate("or", 0, 0, 0, 0));
    }

    @Test
    public void parseFloat() {
        assertEquals(3.14, evaluate("parse-float", "3.14"));
        assertEquals(3.0, evaluate("parse-float", "3"));
        assertNull(evaluate("parse-float", "not-a-float"));
    }

    @Test
    public void parseInt() {
        assertEquals(3, evaluate("parse-int", "3"));
        assertNull(evaluate("parse-int", "3.14"));
        assertNull(evaluate("parse-int", "not-an-int"));
    }

    @Test
    public void pi() {
        assertEquals(Math.PI, evaluate("pi"));
    }

    @Test
    public void println() {
        assertNull(evaluate("println", "We're just going to assume", "that this actually printed something"));
    }

    @Test
    public void printlnErr() {
        assertNull(evaluate("println-err", "We're just going to assume", "that this actually printed something to System.err"));
    }

    @Test
    public void procedurep() {
        assertEquals(true, evaluate("procedure?", new Symbol("begin")));
        assertEquals(true, evaluate("procedure?", new Symbol("+")));
        assertEquals(false, evaluate("procedure?", new Symbol("pi")));
        assertEquals(false, evaluate("procedure?", new Symbol("undefined-symbol")));
        assertEquals(false, evaluate("procedure?", 0));
        assertEquals(false, evaluate("procedure?", "begin"));
    }

    @Test
    public void range() {
        assertEquals(List.of(3, 4, 5, 6), evaluate("range", 3, 7));
    }

    @Test
    public void reverse() {
        assertEquals(List.of(4, 3, 2, 1), evaluate("reverse", List.of(1, 2, 3, 4)));
    }

    @Test
    public void round() {
        assertEquals(42, evaluate("round", 42.42));
        assertEquals(42, evaluate("round", 41.52));
        assertEquals(42, evaluate("round", "41.52"));
        assertEquals(42, evaluate("round", new Symbol("41.52")));
    }

    @Test
    public void strCharAt() {
        assertEquals("c", evaluate("str/char-at", 2, "abcde"));
        assertEquals("c", evaluate("str/char-at", 2.0, "abcde"));
        assertEquals("a", evaluate("str/char-at", 0, "abcde"));
    }

    @Test
    public void strConcat() {
        assertEquals("Hello World!", evaluate("str/concat", "Hello", " ", "World", "!"));
        assertEquals("abc42xyz", evaluate("str/concat", "abc", 4, 2, "xyz"));
    }

    @Test
    public void strEndsWithp() {
        assertEquals(true, evaluate("str/ends-with?", "de", "abcde"));
        assertEquals(false, evaluate("str/ends-with?", "def", "abcde"));
    }

    @Test
    public void strIndexOf() {
        assertEquals(2, evaluate("str/index-of", "c", "abcde"));
        assertEquals(0, evaluate("str/index-of", "a", "abcde"));
        assertEquals(-1, evaluate("str/index-of", "x", "abcde"));
    }

    @Test
    public void strJoin() {
        assertEquals("a,b,c", evaluate("str/join", ",", List.of("a", "b", "c")));
        assertEquals("a", evaluate("str/join", ",", List.of("a")));
    }

    @Test
    public void strLength() {
        assertEquals(10, evaluate("str/length", "abcdefghij"));
        assertEquals(0, evaluate("str/length", ""));
    }

    @Test
    public void strReplace() {
        assertEquals("hello world", evaluate("str/replace", "hello lisp", "lisp", "world"));
        assertEquals("x ( y", evaluate("str/replace", "x(y", "(", " ( "));
    }

    @Test
    public void strSplit() {
        assertEquals(List.of("a", "b", "c"), evaluate("str/split", ",", "a,b,c"));
        assertEquals(List.of("abc"), evaluate("str/split", ",", "abc"));
    }

    @Test
    public void strStartsWithp() {
        assertEquals(true, evaluate("str/starts-with?", "ab", "abcde"));
        assertEquals(false, evaluate("str/starts-with?", "abx", "abcde"));
    }

    @Test
    public void strSubstring() {
        assertEquals("string", evaluate("str/substring", "'string'", 1, 7));
        assertEquals("", evaluate("str/substring", "something", 0, 0));
    }

    @Test
    public void strToLower() {
        assertEquals("abcde", evaluate("str/to-lower", "aBcDe"));
    }

    @Test
    public void strToUpper() {
        assertEquals("ABCDE", evaluate("str/to-upper", "aBcDe"));
    }

    @Test
    public void stringp() {
        assertEquals(true, evaluate("string?", "string"));
        assertEquals(true, evaluate("string?", "string'"));
        assertEquals(true, evaluate("string?", "'string"));
        assertEquals(true, evaluate("string?", "'string'"));
        assertEquals(false, evaluate("string?", new Symbol("symbol")));
        assertEquals(false, evaluate("string?", 42));
    }

    @Test
    public void symbol() {
        assertEquals(new Symbol("some-symbol"), evaluate("symbol", "some-symbol"));
        assertEquals(new Symbol("+"), evaluate("symbol", "+"));
        assertEquals(new Symbol("42"), evaluate("symbol", 42));
    }

    @Test
    public void symbolp() {
        assertEquals(true, evaluate("symbol?", new Symbol("symbol")));
        assertEquals(false, evaluate("symbol?", "string"));
        assertEquals(false, evaluate("symbol?", "string'"));
        assertEquals(false, evaluate("symbol?", "'string"));
        assertEquals(false, evaluate("symbol?", "'string'"));
        assertEquals(false, evaluate("symbol?", 42));
    }

    @Test
    public void tail() {
        assertEquals(List.of(2, 3), evaluate("tail", List.of(1, 2, 3)));
        assertEquals(List.empty(), evaluate("tail", List.of(1)));
        assertEquals(List.empty(), evaluate("tail", List.empty()));
        assertEquals(null, evaluate("tail"));
        assertEquals(null, evaluate("tail", new Symbol("not-a-list")));
    }

    @Test
    public void throwException() {
        var rte = assertThrows(RuntimeException.class, () -> evaluate("throw", "some-exception"));
        assertEquals("some-exception", rte.getMessage());
    }

    private Object evaluate(String symbol, Object... params) {
        var sut = ENV.lookup(new Symbol(symbol));
        if (sut instanceof Value) {
            return sut.value();
        }
        return sut.evaluate(List.of(params), ENV, null);
    }
}

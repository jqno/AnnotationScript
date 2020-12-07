package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Option;
import nl.jqno.annotationscript.language.ast.AstSymbol;
import nl.jqno.annotationscript.language.fn.Value;

public class GlobalEnvironmentTest {
    private static final Environment ENV = GlobalEnvironment.build();

    @Test
    public void addition() {
        assertEquals(1.0, evaluate("+", 1.0));
        assertEquals(42, evaluate("+", 3, 33, 6));
        assertEquals(42.0, evaluate("+", 3, 33.0, 6));
        assertEquals(42.0, evaluate("+", "3", 33.0, 6));
    }

    @Test
    public void subtraction() {
        assertEquals(1.0, evaluate("-", 1.0));
        assertEquals(10, evaluate("-", 42, 30, 2));
        assertEquals(10.0, evaluate("-", 42, 30.0, 2));
        assertEquals(10.0, evaluate("-", 42, 30, "2"));
    }

    @Test
    public void multiplication() {
        assertEquals(1.0, evaluate("*", 1.0));
        assertEquals(24, evaluate("*", 2, 3, 4));
        assertEquals(24.0, evaluate("*", 2, 3.0, 4));
        assertEquals(24.0, evaluate("*", 2, "3.0", 4));
    }

    @Test
    public void division() {
        assertEquals(1, evaluate("/", 1));
        assertEquals(2, evaluate("/", 10, 2, 2));
        assertEquals(2.5, evaluate("/", 10, 2.0, 2));
        assertEquals(2.5, evaluate("/", "10", 2.0, 2));
    }

    @Test
    public void modulo() {
        assertEquals(1.0, evaluate("%", 1.0));
        assertEquals(2, evaluate("%", 17, 6, 3));
        assertEquals(2.0, evaluate("%", 17, 6.0, 3));
        assertEquals(2.0, evaluate("%", 17, "6.0", 3));
    }

    @Test
    public void greaterThan() {
        assertEquals(1, evaluate(">", 10, 4));
        assertEquals(0, evaluate(">", 10, 10));
        assertEquals(0, evaluate(">", 4, 10));
    }

    @Test
    public void lessThan() {
        assertEquals(1, evaluate("<", 4, 10));
        assertEquals(0, evaluate("<", 10, 10));
        assertEquals(0, evaluate("<", 10, 4));
    }

    @Test
    public void greaterThanOrEqualTo() {
        assertEquals(1, evaluate(">=", 10, 4));
        assertEquals(1, evaluate(">=", 10, 10));
        assertEquals(0, evaluate(">=", 4, 10));
    }

    @Test
    public void lessThanOrEqualTo() {
        assertEquals(1, evaluate("<=", 4, 10));
        assertEquals(1, evaluate("<=", 10, 10));
        assertEquals(0, evaluate("<=", 10, 4));
    }

    @Test
    public void equalTo() {
        assertEquals(1, evaluate("=", 10, 10));
        assertEquals(0, evaluate("=", 10, 4));
        assertEquals(1, evaluate("=", 0, 0.0));
        assertEquals(0, evaluate("=", "a", 0.0));
        assertEquals(0, evaluate("=", 0, "b"));
        assertEquals(1, evaluate("=", "a", "a"));
        assertEquals(0, evaluate("=", "a", "b"));
        assertEquals(1, evaluate("=", "'a'", "'a'"));
        assertEquals(0, evaluate("=", "'a'", "'b'"));
    }

    @Test
    public void abs() {
        assertEquals(1.0, evaluate("abs", -1.0));
        assertEquals(1.0, evaluate("abs", 1.0));
        assertEquals(1, evaluate("abs", 1));
        assertEquals(1, evaluate("abs", 1));
    }

    @Test
    public void and() {
        assertEquals(1, evaluate("and", 1, 42));
        assertEquals(0, evaluate("and", 0, 42));
        assertEquals(0, evaluate("and", 1, 0));
        assertEquals(0, evaluate("and", 0, 0));

        assertEquals(1, evaluate("and", 1, 42, 1, 1));
        assertEquals(0, evaluate("and", 1, 42, 0, 1));
        assertEquals(0, evaluate("and", 0, 0, 0, 0));
    }

    @Test
    public void append() {
        assertEquals(List.of(1, 2, 3, 42), evaluate("append", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("append", 42, List.empty()));
    }

    @Test
    public void apply() {
        assertEquals(42, evaluate("apply", ENV.lookup("+"), List.of(10, 32)));
        assertEquals(1, evaluate("apply", ENV.lookup("number?"), List.of(42)));
    }

    @Test
    public void atomp() {
        assertEquals(1, evaluate("atom?", 42));
        assertEquals(1, evaluate("atom?", 42.0));
        assertEquals(1, evaluate("atom?", "string"));
        assertEquals(0, evaluate("atom?", new AstSymbol("symbol")));
        assertEquals(0, evaluate("atom?", List.empty()));
    }

    @Test
    public void begin() {
        assertEquals(1, evaluate("begin", 1));
        assertEquals("z", evaluate("begin", "x", "y", "z"));
    }

    @Test
    public void cons() {
        assertEquals(List.of(42, 1, 2, 3), evaluate("cons", 42, List.of(1, 2, 3)));
        assertEquals(List.of(42), evaluate("cons", 42, List.empty()));
    }

    @Test
    public void containsp() {
        assertEquals(1, evaluate("contains?", List.of(1, 2, 3), 2));
        assertEquals(0, evaluate("contains?", List.of(1, 2, 3), 4));
        assertEquals(1, evaluate("contains?", List.of(1, "two", "'three'"), "two"));
    }

    @Test
    public void dec() {
        assertEquals(42, evaluate("dec", 43));
        assertEquals(42.0, evaluate("dec", 43.0));
    }

    @Test
    public void else_() {
        assertEquals(1, evaluate("else"));
    }

    @Test
    public void filter() {
        assertEquals(List.of(1, 3.0), evaluate("filter", ENV.lookup("number?"), List.of(1, "two", 3.0)));
    }

    @Test
    public void foldLeft() {
        assertEquals(42, evaluate("fold-left", ENV.lookup("+"), 10, List.of(8, 4, 12, 8)));
    }

    @Test
    public void head() {
        assertEquals(1, evaluate("head", List.of(1, 2, 3)));
        assertEquals(null, evaluate("head", List.empty()));
        assertEquals(null, evaluate("head"));
        assertEquals(null, evaluate("head", "not-a-list"));
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
        assertNull(evaluate("length", "not-a-list"));
    }

    @Test
    public void list() {
        assertEquals(List.of(1, 2, 3), evaluate("list", 1, 2, 3));
        assertEquals(List.empty(), evaluate("list"));
    }

    @Test
    public void listp() {
        assertEquals(1, evaluate("list?", List.of(1, 2, 3)));
        assertEquals(1, evaluate("list?", List.empty()));
        assertEquals(0, evaluate("list?", "not-a-list"));
        assertEquals(0, evaluate("list?", 1));
    }

    @Test
    public void map() {
        assertEquals(List.of(1, 0, 1), evaluate("map", ENV.lookup("number?"), List.of(1, "two", 3.0)));
    }

    @Test
    public void mapContainsp() {
        assertEquals(1, evaluate("map/contains?", HashMap.of("a", 42, "b", 10), "a"));
        assertEquals(0, evaluate("map/contains?", HashMap.of("a", 42, "b", 10), "c"));
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
        assertEquals(42, evaluate("map/get", HashMap.of("a", 42, "b", 10), "a"));
        assertNull(evaluate("map/get", HashMap.empty(), "a"));
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
        assertEquals(HashMap.of("'a'", 42, "'b'", 10), evaluate("map/of", "'a'", 42, "'b'", 10));
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
    }

    @Test
    public void min() {
        assertEquals(42.0, evaluate("min", 42.0));
        assertEquals(3, evaluate("min", 6, 42, 3));
        assertEquals(3.0, evaluate("min", 6, 42.0, 3));
        assertEquals(3.0, evaluate("min", "6", 42, 3));
    }

    @Test
    public void not() {
        assertEquals(1, evaluate("not", 0));
        assertEquals(1, evaluate("not", 0.0));
        assertEquals(0, evaluate("not", 1));
        assertEquals(0, evaluate("not", 2));
        assertEquals(0, evaluate("not", "something"));
        assertEquals(0, evaluate("not", List.empty()));
    }

    @Test
    public void null_() {
        assertNull(evaluate("null"));
    }

    @Test
    public void nullp() {
        assertEquals(1, evaluate("null?", Option.none().getOrNull())); // avoid warnings caused by typing `null`
                                                                       // directly
        assertEquals(0, evaluate("null?", "null"));
        assertEquals(0, evaluate("null?", 1));
    }

    @Test
    public void numberp() {
        assertEquals(1, evaluate("number?", 1));
        assertEquals(1, evaluate("number?", 1.0));
        assertEquals(0, evaluate("number?", "not-a-number"));
        assertEquals(0, evaluate("number?", "'not-a-number'"));
        assertEquals(0, evaluate("number?", List.empty()));
    }

    @Test
    public void or() {
        assertEquals(1, evaluate("or", 1, 42));
        assertEquals(1, evaluate("or", 0, 42));
        assertEquals(1, evaluate("or", 1, 0));
        assertEquals(0, evaluate("or", 0, 0));

        assertEquals(1, evaluate("or", 1, 42, 1, 1));
        assertEquals(1, evaluate("or", 0, 42, 0, 0));
        assertEquals(0, evaluate("or", 0, 0, 0, 0));
    }

    @Test
    public void pi() {
        assertEquals(Math.PI, evaluate("pi"));
    }

    @Test
    public void println() {
        assertNull(evaluate("println", "'We're just going to assume'", "'that this actually printed something'"));
    }

    @Test
    public void printlnErr() {
        assertNull(evaluate("println-err", "'We're just going to assume'", "'that this actually printed something'",
                "'to System.err'"));
    }

    @Test
    public void procedurep() {
        assertEquals(1, evaluate("procedure?", "begin"));
        assertEquals(1, evaluate("procedure?", "+"));
        assertEquals(0, evaluate("procedure?", "pi"));
        assertEquals(0, evaluate("procedure?", "undefined-symbol"));
        assertEquals(0, evaluate("procedure?", 0));
        assertEquals(0, evaluate("procedure?", "'begin'"));
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
        assertEquals(1, evaluate("str/ends-with?", "de", "abcde"));
        assertEquals(0, evaluate("str/ends-with?", "def", "abcde"));
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
        assertEquals(1, evaluate("str/starts-with?", "ab", "abcde"));
        assertEquals(0, evaluate("str/starts-with?", "abx", "abcde"));
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
        assertEquals(1, evaluate("string?", "string"));
        assertEquals(1, evaluate("string?", "string'"));
        assertEquals(1, evaluate("string?", "'string"));
        assertEquals(1, evaluate("string?", "'string'"));
        assertEquals(0, evaluate("string?", new AstSymbol("symbol")));
        assertEquals(0, evaluate("string?", 42));
    }

    @Test
    public void symbolp() {
        assertEquals(1, evaluate("symbol?", new AstSymbol("symbol")));
        assertEquals(0, evaluate("symbol?", "string"));
        assertEquals(0, evaluate("symbol?", "string'"));
        assertEquals(0, evaluate("symbol?", "'string"));
        assertEquals(0, evaluate("symbol?", "'string'"));
        assertEquals(0, evaluate("symbol?", 42));
    }

    @Test
    public void tail() {
        assertEquals(List.of(2, 3), evaluate("tail", List.of(1, 2, 3)));
        assertEquals(List.empty(), evaluate("tail", List.of(1)));
        assertEquals(List.empty(), evaluate("tail", List.empty()));
        assertEquals(null, evaluate("tail"));
        assertEquals(null, evaluate("tail", "not-a-list"));
    }

    private Object evaluate(String symbol, Object... params) {
        var sut = ENV.lookup(symbol);
        if (sut instanceof Value) {
            return sut.value();
        }
        return sut.evaluate(List.of(params), ENV, null);
    }
}

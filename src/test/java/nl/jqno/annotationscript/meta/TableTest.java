package nl.jqno.annotationscript.meta;

import static nl.jqno.annotationscript.AnnotationScript.run;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import nl.jqno.annotationscript.Annotations.*;
import nl.jqno.annotationscript.language.fn.Fn;

public class TableTest {

    private static final Object IDENTITY = Fn.builtin("identity", params -> params.get(0));

    @Nested
    class NewEntry {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.NewEntry.class)
        @Zero(list={@One("new-entry"), @One("s1"), @One("s2")})
        class Sut {}

        @Test
        public void happy() {
            var initialValues = input("s1", 1, "s2", 2);
            var actual = run(Sut.class, initialValues);
            assertEquals(List.of(1, 2), actual);
        }
    }

    @Nested
    class LookupInEntry {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.LookupInEntry.class)
        @Zero(include=Table.LookupInEntryHelp.class)
        @Zero(list={@One("lookup-in-entry"), @One("name"), @One("entry"), @One("entry-f")})
        class Sut {}

        private final Object entry = List.of(
            List.of("appetizer", "entree", "beverage"),
            List.of("pate", "boeuf", "vin"));
        @Test
        public void entree() {
            var initialValues = input("name", "entree", "entry", entry, "entry-f", IDENTITY);
            var actual = run(Sut.class, initialValues);
            assertEquals("boeuf", actual);
        }

        @Test
        public void noSuchItem() {
            var initialValues = input("name", "no-such-entry", "entry", entry, "entry-f", IDENTITY);
            var actual = run(Sut.class, initialValues);
            assertEquals("no-such-entry", actual);
        }
    }

    @Nested
    class ExtendTable {
        @Zero("begin")
        @Zero(include=Table.ExtendTable.class)
        @Zero(list={@One("extend-table"), @One("h"), @One("t")})
        class Sut {}

        @Test
        public void extendTable() {
            var initalValues = input("h", 1, "t", List.of(2, 3));
            var actual = run(Sut.class, initalValues);
            assertEquals(List.of(1, 2, 3), actual);
        }
    }

    @Nested
    class LookupInTable {
        @Zero("begin")
        @Zero(include=Helpers.class)
        @Zero(include=Table.class)
        @Zero(list={@One("lookup-in-table"), @One("name"), @One("table"), @One("table-f")})
        class Sut {}

        private final Object table = List.of(
            List.of(List.of("entree", "dessert"), List.of("spaghetti", "spumoni")),
            List.of(List.of("appetizer", "entree", "beverage"), List.of("food", "tastes", "good")));

        @Test
        public void beverage() {
            var initialValues = input("name", "beverage", "table", table, "table-f", IDENTITY);
            var actual = run(Sut.class, initialValues);
            assertEquals("good", actual);
        }

        @Test
        public void noSuchItem() {
            var initialValues = input("name", "no-such-item", "table", table, "table-f", IDENTITY);
            var actual = run(Sut.class, initialValues);
            assertEquals("no-such-item", actual);
        }
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2) {
        return HashMap.of(key1, val1, key2, val2);
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2, String key3, Object val3) {
        return HashMap.of(key1, val1, key2, val2, key3, val3);
    }
}

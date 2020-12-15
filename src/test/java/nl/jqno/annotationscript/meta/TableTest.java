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
        private final Object entryF = Fn.builtin("identity", params -> params.get(0));

        @Test
        public void entree() {
            var initialValues = input("name", "entree", "entry", entry, "entry-f", entryF);
            var actual = run(Sut.class, initialValues);
            assertEquals("boeuf", actual);
        }

        @Test
        public void noSuchItem() {
            var initialValues = input("name", "no-such-entry", "entry", entry, "entry-f", entryF);
            var actual = run(Sut.class, initialValues);
            assertEquals("no-such-entry", actual);
        }
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2) {
        return HashMap.of(key1, val1, key2, val2);
    }

    private Map<String, Object> input(String key1, Object val1, String key2, Object val2, String key3, Object val3) {
        return HashMap.of(key1, val1, key2, val2, key3, val3);
    }
}

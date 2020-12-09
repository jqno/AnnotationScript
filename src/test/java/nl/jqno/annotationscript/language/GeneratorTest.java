package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.vavr.collection.List;

public class GeneratorTest {
    private Generator sut = new Generator();

    @Test
    public void generateFloat() {
        var input = List.of(4.2);
        var actual = sut.generate(input);
        assertEquals("@Zero(\"4.2\")", actual);
    }

    @Test
    public void generateInt() {
        var input = List.of(42);
        var actual = sut.generate(input);
        assertEquals("@Zero(\"42\")", actual);
    }

    @Test
    public void generateString() {
        var input = List.of("forty-two");
        var actual = sut.generate(input);
        assertEquals("@Zero(\"'forty-two'\")", actual);
    }

    @Test
    public void generateSymbol() {
        var input = List.of(new Symbol("forty-two"));
        var actual = sut.generate(input);
        assertEquals("@Zero(\"forty-two\")", actual);
    }

    @Test
    public void generateTopLevelList() {
        var input = List.of(new Symbol("x"), new Symbol("y"), new Symbol("z"));
        var actual = sut.generate(input);
        assertEquals("@Zero(\"x\")@Zero(\"y\")@Zero(\"z\")", actual);
    }

    @Test
    public void generateNestedList() {
        var input = List.of(List.of(new Symbol("x"), new Symbol("y"), new Symbol("z")));
        var actual = sut.generate(input);
        assertEquals("@Zero(list={@One(\"x\"), @One(\"y\"), @One(\"z\")})", actual);
    }

    @Test
    public void generateLevels() {
        var input = List.of(List.of(List.of(List.of(List.of(List.of(List.of(
            List.of(List.of(List.of(List.of(List.of(new Symbol("😜")))))))))))));
        var actual = sut.generate(input);
        var expected = "@Zero(list={@One(list={@Two(list={@Three(list={@Four(list={@Five(list=" +
            "{@Six(list={@Seven(list={@Eight(list={@Nine(list={@Ten(list={@Eleven(\"😜\")})})})})})})})})})})})";
        assertEquals(expected, actual);
    }
}

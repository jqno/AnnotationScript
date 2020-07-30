package nl.jqno.annotationscript.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.jqno.annotationscript.language.ast.*;

public class GeneratorTest {
    private Generator sut = new Generator();

    @Test
    public void generateFloat() {
        var input = new AstFloat(4.2);
        var actual = sut.generate(input);
        assertEquals("@Zero(\"4.2\")", actual);
    }

    @Test
    public void generateInt() {
        var input = new AstInt(42);
        var actual = sut.generate(input);
        assertEquals("@Zero(\"42\")", actual);
    }

    @Test
    public void generateString() {
        var input = new AstString("forty-two");
        var actual = sut.generate(input);
        assertEquals("@Zero(\"'forty-two'\")", actual);
    }

    @Test
    public void generateSymbol() {
        var input = new AstSymbol("forty-two");
        var actual = sut.generate(input);
        assertEquals("@Zero(\"forty-two\")", actual);
    }

    @Test
    public void generateList() {
        var input = new AstList(new AstSymbol("x"), new AstSymbol("y"), new AstSymbol("z"));
        var actual = sut.generate(input);
        assertEquals("@Zero(list={@One(\"x\"), @One(\"y\"), @One(\"z\")})", actual);
    }

    @Test
    public void generateLevels() {
        var input = new AstList(new AstList(new AstList(new AstList(new AstList(new AstList(new AstList(
            new AstList(new AstList(new AstList(new AstList(new AstSymbol("😜"))))))))))));
        var actual = sut.generate(input);
        var expected = "@Zero(list={@One(list={@Two(list={@Three(list={@Four(list={@Five(list=" +
            "{@Six(list={@Seven(list={@Eight(list={@Nine(list={@Ten(list={@Eleven(\"😜\")})})})})})})})})})})})";
        assertEquals(expected, actual);
    }
}

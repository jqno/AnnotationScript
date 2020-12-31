![AnnotationScript](https://github.com/jqno/AnnotationScript/workflows/AnnotationScript/badge.svg)

# AnnotationScript

AnnotationScript is a functional, dynamically typed language that [starts numbering at zero](https://www.cs.utexas.edu/users/EWD/transcriptions/EWD08xx/EWD831.html), and is contained within annotations placed on a Java class.

A top-level expression may look like this:

```java
@Zero("+")@Zero("x")@Zero("1")
class Example {}
```

Each element of the expression is placed within its own `@Zero` annotation. The function to be called comes first; its parameters follow. This expression calculates `x + 1`.

You can create more complex expressions by using _lists_, in which the name of the annotation is incremented by one, like this:

```java
@Zero("+")@Zero(list={@One("*"), @One("x"), @One("2")})@Zero("1")
class Example {}
```

This calculates `(x * 2) + 1`.

You can define constants as follows:

```java
@Zero("begin")
@Zero(list={@One("define"), @One("TWO"), @One("2")})
@Zero(list={
  @One("+"),
  @One(list={@Two("*"), @Two("x"), @Two("TWO")}),
  @One("1")})
class Example {}
```

This assigns the value `2` to the identifier `TWO`, and then calculates `(x * TWO) + 1`. Since this program now consists of two expressions (the `define` and the calculation itself), we need to wrap the expressions in a `begin` expression, which accepts an arbitrary number of expressions, evaluates each one in turn, and returns the result of the last expression.

We can also define functions:

```java
@Zero("begin")
@Zero(list={
  @One("define"),
  @One("twice"),
  @One(list={
    @Two("lambda"),
    @Two(list={@Three("x")}),
    @Two(list={@Three("*"), @Three("x"), @Three("2")})})})
@Zero(list={
  @One("+"),
  @One(list={@Two("twice"), @Two("x")}),
  @One("1")})
class Example {}
```

As you can see, defining a function is the same as assigning a lambda-expression to an identifier. In this case, we define the lambda `x -> x * 2` to the identifier `twice`. Then we calculate the result of `twice(x) + 1`.

AnnotationScript also supports namespaces:

```java
@Zero("define"),
@Zero("twice"),
@Zero(list={
  @One("lambda"),
  @One(list={@Two("x")}),
  @One(list={@Two("*"), @Two("x"), @Two("2")})})})
class Twice {}

@Zero("begin")
@Zero(include=Twice.class)
@Zero(list={
  @One("+"),
  @One(list={@Two("twice"), @Two("x")}),
  @One("1")})
class Example {}
```

Here, we've moved the `twice` function to the class `Twice`, which we imported in `Example` by using `@Zero(include=Twice.class)`.

We can also group namespaces together. Lets assume we have classes `Twice`, `Thrice` and `Quadruple`:

```java
@Zero(export={
  Twice.class,
  Thrice.class,
  Quadruple.class})
class Module {}

@Zero("begin")
@Zero(include=Module.class)
@Zero(list={
  @One("+"),
  @One(list={@Two("twice"), @Two("x")}),
  @One("1")})
class Example {}
```

`Example` now has access to `Double`, `Thrice` and `Quadruple` through the `Module` class.

AnnotationScript supports branching using `if` and `cond`:

```java
@Zero("if"),
@Zero(list={@One(">"), @One("x"), @One("0")}),
@Zero("positive")
@Zero("negative")
class If {}

@Zero("cond"),
@Zero(list={@One(">"), @One("x"), @One("0")}),
@Zero("positive")
@Zero(list={@One("&lt;"), @One("x"), @One("0")})
@Zero("negative")
@Zero("else")
@Zero("zero")
class Cond {}
```

There are no loops; instead, AnnotationScript uses recursion to achieve the same effect.

AnnotationScript has a lot of built-in functions to do arithmetic, manipulate strings, lists and maps, print to the console, and more. For an exhaustive list, I'll just refer to the [global environment](https://github.com/jqno/AnnotationScript/blob/fac137d719b084b5fc953006886c58516222d791/src/main/java/nl/jqno/annotationscript/language/GlobalEnvironment.java#L22).

Finally, to run an AnnotationScript program, you pass the class that holds the main expression to the `AnnotationScript.run` function:

```java
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

@Zero("+")@Zero("1")@Zero("1")
public class Example {
    public static void main(String[] args) {
        var output = AnnotationScript.run(Example.class);
        System.out.println(output);
    }
}
```

This program outputs `2`.

You can also pass parameters. Note that you need a [Vavr](https://www.vavr.io/) `HashMap` to do so:

```java
import io.vavr.collection.HashMap;
import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.*;

@Zero("+")@Zero("x")@Zero("1")
public class Example {
    public static void main(String[] args) {
        var params = HashMap.of("x", 2)
        var output = AnnotationScript.run(Example.class, params);
        System.out.println(output);
    }
}
```

This program prints `3`.


//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo.meta;

import nl.jqno.annotationscript.meta.MetaScript;

public class HelloWorld {

    public static void main(String[] args) {
        var helloWorld = """
            (cons
              (quote hello)
              (cons
                (quote world)
                (quote ())))
            """;
        var output = MetaScript.run(helloWorld);
        System.out.println(output); // CHECKSTYLE OFF: Regexp
    }
}

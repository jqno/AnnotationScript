//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo;

import nl.jqno.annotationscript.AnnotationScript;
import nl.jqno.annotationscript.Annotations.Zero;

@Zero("println")
@Zero("'Hello world!'")
public class HelloWorld {
    public static void main(String[] args) {
        AnnotationScript.run(HelloWorld.class);
    }
}

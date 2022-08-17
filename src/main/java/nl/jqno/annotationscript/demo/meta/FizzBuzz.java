//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo.meta;

import nl.jqno.annotationscript.meta.MetaScript;

public class FizzBuzz {

    public static void main(String[] args) {
        var fizzbuzz =
            "(define" +
            "  (fizzbuzz" +
            "    (lambda (n)" +
            "      (cond" +
            "        ((eq? 0 (mod n 15)) (quote fizzbuzz))" +
            "        ((eq? 0 (mod n 3)) (quote fizz))" +
            "        ((eq? 0 (mod n 5)) (quote buzz))" +
            "        (else n))))" +
            "  (define" +
            "    (run" +
            "      (lambda (i end recurse)" +
            "        (cond" +
            "          ((eq? i end) (quote ()))" +
            "          (else" +
            "            (cons (fizzbuzz i) (recurse (add1 i) end recurse))))))" +
            "    (run 1 50 run)))";
        var output = MetaScript.run(fizzbuzz);
        System.out.println(output); // CHECKSTYLE OFF: Regexp
    }
}

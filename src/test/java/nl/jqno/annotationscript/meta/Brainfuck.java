//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.meta;

import org.junit.jupiter.api.Test;

public class Brainfuck {

    public static final String HELLO_WORLD = ">+++++++++[<++++++++>-]<.>+++++++[<++++>-]<+.+++++++..+++.>>>++++++++[<++++>-]" +
        "<.>>>++++++++++[<+++++++++>-]<---.<<<<.+++.------.--------.>>+.>++++++++++.";

    @Test
    public void main() {
        var brainfuck = """
            (define (fold-left
              (lambda (func init lst)
                ((lambda (recurse)
                   (recurse recurse func init lst))
                 (lambda (recurse func init lst)
                   (cond
                    ((null? lst) init)
                    (else (recurse recurse func (func init (car lst)) (cdr lst))))))))

            (define (nth
                (lambda (n lst)
                  (car (cdr
                    (fold-left
                      (lambda (acc elem)
                        (cond
                          ((eq? (car acc) n) (cons elem (cons elem (quote ()))))
                          (else (cons (+ 1 (car acc)) (cons (car (cdr acc)) (quote ()))))))
                      (cons 0 (cons 0 (quote ())))
                      lst)))))

            (nth 2 (quote (1 2 3 4 5)))))
            """;
        var output = MetaScript.run(brainfuck);
        System.out.println(output); // CHECKSTYLE OFF: Regexp
    }
}

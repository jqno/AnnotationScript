//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.meta;

import io.vavr.collection.List;

import org.junit.jupiter.api.Test;

public class Brainfuck {

    @Test
    public void main() {
        var brainfuck = """
            (define (program (quote (
                > + + + + + + + + + [ < + + + + + + + + > - ] < . > + + + + + + + [ < + + + + > - ] < + . + + + + + + + . . + + + . > > > +
                + + + + + + + [ < + + + + > - ] < . > > > + + + + + + + + + + [ < + + + + + + + + + > - ] < - - - . < < < < . + + + . - - -
                - - - . - - - - - - - - . > > + . > + + + + + + + + + + .
            )))

            (define (fold-left
              (lambda (func init lst)
                ((lambda (recurse)
                   (recurse recurse func init lst))
                 (lambda (recurse func init lst)
                   (cond
                    ((null? lst) init)
                    (else (recurse recurse func (func init (car lst)) (cdr lst))))))))

            (define (create-state
              (lambda (tape pointer program-counter output stack)
                (cons tape (cons pointer (cons program-counter (cons output (cons stack (quote ()))))))))

            (define (initial-tape (quote (0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)))

            (define (execute
              (lambda (state cmd)
                (define (tape (car state))
                (define (pointer (car (cdr state)))
                (define (program-counter (car (cdr (cdr state))))
                (define (output (car (cdr (cdr (cdr state)))))
                (define (stack (car (cdr (cdr (cdr (cdr state))))))

                (cond
                  ((eq? cmd (quote >))
                   (create-state tape (+ pointer 1) (+ program-counter 1) output stack))
                  ((eq? cmd (quote <))
                   (create-state tape (- pointer 1) (+ program-counter 1) output stack))
                  ((eq? cmd (quote +))
                   (create-state (update-nth! pointer (+ (nth! pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote -))
                   (create-state (update-nth! pointer (- (nth! pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote .))
                   (create-state tape pointer (+ program-counter 1) (cons (nth! pointer tape) output) stack))
                  ((eq? cmd (quote [))
                   (cond
                     ((zero? (nth! pointer tape))
                      (create-state tape pointer (🤪) output (cons program-counter stack)))
                     (else
                      (create-state tape pointer (+ program-counter 1) output (cons program-counter stack)))))
                  ((eq? cmd (quote ]))
                   (cond
                     ((zero? (nth! pointer tape))
                      (create-state tape pointer (+ program-counter 1) output (cdr stack)))
                     (else
                      (create-state tape pointer (car stack) output (cdr stack)))))
                  (else
                   state)))))))))

            (define (helper
              (lambda (recurse prg state)
                (define (program-counter (car (cdr (cdr state))))
                (cond
                  ((< program-counter (length! prg))
                   (recurse recurse prg (execute state (nth! program-counter prg))))
                  (else
                   (reverse! (car (cdr (cdr (cdr state))))))))))

            (define (bf-interpreter
              (lambda (prg)
                (helper helper prg (create-state initial-tape 0 0 (quote ()) (quote ())))))

            (bf-interpreter program))))))))
            """;
        var output = MetaScript.run(brainfuck);
        System.out.println(output); // CHECKSTYLE OFF: Regexp
        System.out.println(((List<Object>)output).map(l -> Character.toString((char)((Integer)l).intValue())).mkString());
    }
}

//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.meta;

import io.vavr.collection.List;

import org.junit.jupiter.api.Test;

/*
 * This implementation of Brainfuck doesn't "cheat" by using functions added natively to MetaScript,
 * just for use in this program.
 *
 * As a consequence, though, it's also very slow.
 */
public class BrainfuckNoCheating {

    @Test
    public void main() {
        var brainfuck = """
            (define (program (quote (
                > + + + + + + + + + [ < + + + + + + + + > - ] < . > + + + + + + + [ < + + + + > - ] < + . + + + + + + + . . + + + . > > > +
                + + + + + + + [ < + + + + > - ] < . > > > + + + + + + + + + + [ < + + + + + + + + + > - ] < - - - . < < < < . + + + . - - -
                - - - . - - - - - - - - . > > + . > + + + + + + + + + + .
            )))

            (define (reverse-helper
              (lambda (recurse acc lst)
                (cond
                 ((null? lst) acc)
                 (else (recurse recurse (cons (car lst) acc) (cdr lst))))))

            (define (reverse
              (lambda (lst)
                (reverse-helper reverse-helper (quote ()) lst)))

            (define (nth
              (lambda (recurse n lst)
                (cond
                  ((null? lst) #f)
                  ((eq? n 0) (car lst))
                  (else (recurse recurse (- n 1) (cdr lst))))))

            (define (update-nth
              (lambda (recurse n val lst)
                (cond
                  ((null? lst) #f)
                  ((eq? n 0) (cons val (cdr lst)))
                  (else (cons (car lst) (recurse recurse (- n 1) val (cdr lst)))))))

            (define (create-state
              (lambda (tape pointer program-counter output stack)
                (cons tape (cons pointer (cons program-counter (cons output (cons stack (quote ()))))))))

            (define (initial-tape (quote (0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)))

            (define (find-corresponding-bracket-helper
              (lambda (recurse counter program-counter prg)
                (cond
                  ((zero? counter)
                   program-counter)
                  ((eq? (nth nth program-counter prg) (quote [))
                   (recurse recurse (+ counter 1) (+ program-counter 1) prg))
                  ((eq? (nth nth program-counter prg) (quote ]))
                   (recurse recurse (- counter 1) (+ program-counter 1) prg))
                  (else
                   (recurse recurse counter (+ program-counter 1) prg)))))

            (define (find-corresponding-bracket
              (lambda (prg program-counter)
                (find-corresponding-bracket-helper find-corresponding-bracket-helper 1 (+ program-counter 1) prg)))

            (define (execute
              (lambda (state cmd prg)
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
                   (create-state (update-nth update-nth pointer (+ (nth nth pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote -))
                   (create-state (update-nth update-nth pointer (- (nth nth pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote .))
                   (create-state tape pointer (+ program-counter 1) (cons (nth nth pointer tape) output) stack))
                  ((eq? cmd (quote [))
                   (cond
                     ((zero? (nth nth pointer tape))
                      (create-state tape pointer (find-corresponding-bracket prg program-counter) output (cons program-counter stack)))
                     (else
                      (create-state tape pointer (+ program-counter 1) output (cons program-counter stack)))))
                  ((eq? cmd (quote ]))
                   (cond
                     ((zero? (nth nth pointer tape))
                      (create-state tape pointer (+ program-counter 1) output (cdr stack)))
                     (else
                      (create-state tape pointer (car stack) output (cdr stack)))))
                  (else
                   state)))))))))

            (define (bf-interpreter-helper
              (lambda (recurse prg state)
                (define (program-counter (car (cdr (cdr state))))
                (cond
                  ((< program-counter (length! prg))
                   (recurse recurse prg (execute state (nth nth program-counter prg) prg)))
                  (else
                   (reverse (car (cdr (cdr (cdr state))))))))))

            (define (bf-interpreter
              (lambda (prg)
                (bf-interpreter-helper bf-interpreter-helper prg (create-state initial-tape 0 0 (quote ()) (quote ())))))

            (bf-interpreter program)))))))))))))
            """;
        var output = MetaScript.run(brainfuck);
        System.out.println(output); // CHECKSTYLE OFF: Regexp
        System.out.println(((List<Object>)output).map(l -> Character.toString((char)((Integer)l).intValue())).mkString());
    }
}

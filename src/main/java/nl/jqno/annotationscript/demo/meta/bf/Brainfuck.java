//REPOS jitpack
//DEPS com.github.jqno:AnnotationScript:main-SNAPSHOT
package nl.jqno.annotationscript.demo.meta.bf;

import nl.jqno.annotationscript.meta.MetaScript;

/*
 * This implementation of Brainfuck "cheats" by using functions added natively to MetaScript,
 * just for use in this program.
 *
 * As a consequence, though, it's fast enough to use in demos 😉
 */
public class Brainfuck {

    public void main() {
        var brainfuck = """
            (define (program (quote (
                > + + + + + + + + + [ < + + + + + + + + > - ] < . > + + + + + + + [ < + + + + > - ] < + . + + + + + + + . . + + + . > > > +
                + + + + + + + [ < + + + + > - ] < . > > > + + + + + + + + + + [ < + + + + + + + + + > - ] < - - - . < < < < . + + + . - - -
                - - - . - - - - - - - - . > > + . > + + + + + + + + + + .
            )))

            (define (create-state
              (lambda (tape pointer program-counter output stack)
                (cons tape (cons pointer (cons program-counter (cons output (cons stack (quote ()))))))))

            (define (initial-tape (quote (0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)))

            (define (find-corresponding-bracket-helper
              (lambda (recurse counter program-counter prg)
                (cond
                  ((zero? counter)
                   program-counter)
                  ((eq? (nth! program-counter prg) (quote [))
                   (recurse recurse (+ counter 1) (+ program-counter 1) prg))
                  ((eq? (nth! program-counter prg) (quote ]))
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
                   (create-state (update-nth! pointer (+ (nth! pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote -))
                   (create-state (update-nth! pointer (- (nth! pointer tape) 1) tape) pointer (+ program-counter 1) output stack))
                  ((eq? cmd (quote .))
                   (create-state tape pointer (+ program-counter 1) (cons (nth! pointer tape) output) stack))
                  ((eq? cmd (quote [))
                   (cond
                     ((zero? (nth! pointer tape))
                      (create-state tape pointer (find-corresponding-bracket prg program-counter) output (cons program-counter stack)))
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

            (define (bf-interpreter-helper
              (lambda (recurse prg state)
                (define (program-counter (car (cdr (cdr state))))
                (cond
                  ((< program-counter (length! prg))
                   (recurse recurse prg (execute state (nth! program-counter prg) prg)))
                  (else
                   (reverse! (car (cdr (cdr (cdr state))))))))))

            (define (bf-interpreter
              (lambda (prg)
                (bf-interpreter-helper bf-interpreter-helper prg (create-state initial-tape 0 0 (quote ()) (quote ())))))

            (bf-interpreter program)))))))))
            """;
        var output = MetaScript.run(brainfuck);

        // CHECKSTYLE OFF: Regexp
        System.out.println(output);
        System.out.println(Converter.asciiArrayToString(output));
    }
}

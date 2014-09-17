package im.fuad.rit.concpar.p1;

import java.util.concurrent.Callable;

import im.fuad.rit.concpar.p1.Mediator;
import im.fuad.rit.concpar.p1.WordOccurrence;

/**
 * This class defines a task for reporting matches during text file search for a given word.
 * Occurrences of the word to be reported are retrieved through a mediator object given at
 * construction time.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class ReportMatches implements Callable<Boolean> {
    private String pattern;
    private Mediator mediator;

    public ReportMatches(String pattern, Mediator mediator) {
        this.pattern = pattern;
        this.mediator = mediator;
    }

    public Boolean call() {
        debug("REPORTING");

        while (true) {
            try {
                WordOccurrence match = mediator.get(this.pattern);

                // Signal that no more occurrences of this word are to be submitted.
                if (match == null) { return true; }

                debug("FREAKING FOUND IT MOTHAFOCKA");

                log(match);

            } catch(InterruptedException e) {
                e.printStackTrace();

                return false;
            }
        }
    }

    private void log(WordOccurrence occurrence) { System.out.println(occurrence); }

    private void debug(String title) { debug(title, ""); }
    private void debug(String title, String body) {
        System.err.println("[" + title + "(" + this.pattern + ")] " + body);
    }
}

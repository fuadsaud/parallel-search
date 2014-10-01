package im.fuad.rit.copads.p1;

import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import im.fuad.rit.copads.p1.WordOccurrence;
import im.fuad.rit.copads.p1.DefaultHashMap;

/**
 * This class acts as a monitor to synchronize the communication between
 * file reader (group 1) threads and match reporter (group 2) threads.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class Mediator {
    private HashMap<String, Queue<WordOccurrence>> occurrences;
    private Boolean shutdown;

    public Mediator() {
        this.occurrences =
            new DefaultHashMap<String, Queue<WordOccurrence>>(
                    new Callable<Queue<WordOccurrence>>() {
                        public Queue<WordOccurrence> call() {
                            return new LinkedList<WordOccurrence>();
                        }
                    });
        this.shutdown = false;
    }

    /**
     * Submit a new word ocurrence to this mediator. It'll enqueue the * occurrence in the
     * respective word's bucket. Wakes up all threads waiting for word occurrences. Words can still
     * be submitted even if the mediator was shutdown, though they won't be returned when #get is
     * called (this is a design oversimplification: in an ideal situation, submitting an ocurrence
     * to a shutdown mediator would raise an exception).
     *
     * @param occurrence the occurrence to be submitted.
     */
    public synchronized void put(WordOccurrence occurrence) {
        if (occurrence == null) return;

        ParallelSearch.debug("[PUT] " + occurrence);

        Queue<WordOccurrence> q = occurrences.get(occurrence.getText());

        q.add(occurrence);

        occurrences.put(occurrence.getText(), q);

        notifyAll();
    }

    /**
     * Get a previously submitted word ocurrence for a given word. In case there's currently no
     * occurence for the given word, the thread asking for it will wait until any word gets
     * submitted to this mediator. If this mediator is currently shutdown whrn this method is
     * called and no occurrences are found to the given word, this method returns null as a form to
     * signal consumers that no more occurrences to that word are to be submitted.
     *
     * @param text the word for which an ocurrence is being searched.
     * @return a previously submitted ocurrence for the requested word or null if the current
     *         mediator is currently shutdown.
     * @see #shutdown
     */
    public synchronized WordOccurrence get(String text) throws InterruptedException {
        while (this.occurrences.get(text).isEmpty()) {
            if (this.shutdown) return null;

            Queue<WordOccurrence> q = occurrences.get(text);
            debug("[GET] " + text + " " + (q.isEmpty() ? "wait..." : "bingo!"));

            wait();
        }

        Queue<WordOccurrence> q = occurrences.get(text);

        return q.poll();
    }

    /**
     * Tells the mediator that no more words are to be submitted. Wakes up all threads waiting for
     * word occurrences.
     *
     * @see #get
     */
    public synchronized void shutdown() {
        this.shutdown = true;

        notifyAll();
    }

    private synchronized void debug(String message) {
        ParallelSearch.debug(message);
    }
}

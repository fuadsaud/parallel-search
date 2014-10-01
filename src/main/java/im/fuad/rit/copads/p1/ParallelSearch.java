package im.fuad.rit.copads.p1;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import im.fuad.rit.copads.p1.ReadFile;
import im.fuad.rit.copads.p1.ReportMatches;
import im.fuad.rit.copads.p1.Mediator;

public class ParallelSearch implements Callable<Boolean> {
    private static final String DEBUG_FLAG = "DEBUG";

    private static Boolean debug = false;

    static {
        debug = System.getenv("DEBUG") != null;
    }

    /**
     * System-wide method for debugging (since there's no need to setup a complicated logging
     * scheme; prints messages to stderr.
     *
     * @param message the message to be displayed.
     */
    public static void debug(String message) {
        if (debug) System.err.println("[DEBUG] " + message);
    }

    private List<String> filenames;
    private List<String> patterns;
    private Mediator mediator;

    /**
     * Initialize the search with a list of filenames and the patterns to be
     * searched.
     */
    public ParallelSearch(List<String> filenames, List<String> patterns) {
        this.filenames = filenames;
        this.patterns  = validatePatterns(patterns);
        this.mediator  = new Mediator();
    }

    private BufferedReader readerForFilename(String filename) throws FileNotFoundException {
        return new BufferedReader(
                new FileReader(
                    new File(filename)));
    }

    public Boolean call() {
        ExecutorService reporters = Executors.newFixedThreadPool(patterns.size());

        for (String pattern : patterns) {
            reporters.submit(new ReportMatches(pattern, mediator));
        }

        ExecutorService readers = Executors.newFixedThreadPool(filenames.size());

        for (String filename : filenames) {
            try {
                readers.submit(
                        new ReadFile(
                            filename, readerForFilename(filename), mediator));
            } catch (FileNotFoundException e) {
                logError(filename + ": no such file exists");

                continue;
            }

        }

        try {
            readers.shutdown();
            readers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            mediator.shutdown();

            reporters.shutdown();
            reporters.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void logError(String message) { System.err.println(message); }

    private List<String> validatePatterns(List<String> patterns) {
        for (String pattern : patterns) {
            for (Character c : pattern.toCharArray()) {
                if (!Character.isAlphabetic(c)) {
                    throw new IllegalArgumentException(
                            pattern + ": pattern contains invalid character");
                }
            }
        }

        return patterns;
    }
}

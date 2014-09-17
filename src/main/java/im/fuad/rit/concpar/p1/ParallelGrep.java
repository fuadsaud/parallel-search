package im.fuad.rit.concpar.p1;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import im.fuad.rit.concpar.p1.WordOccurrence;
import im.fuad.rit.concpar.p1.ReadFile;
import im.fuad.rit.concpar.p1.ReportMatches;
import im.fuad.rit.concpar.p1.Mediator;

public class ParallelGrep implements Callable<Boolean> {
    private List<String> filenames;
    private List<String> patterns;
    private Mediator mediator;

    public ParallelGrep(List<String> args) {
        this.filenames = Arrays.asList(args.get(0).split(","));
        this.patterns  = Arrays.asList(args.get(1).split(","));
        this.mediator = new Mediator();
    }

    private BufferedReader readerForFilename(String filename) throws IllegalArgumentException {
        try {
            return new BufferedReader(
                    new FileReader(
                        new File(filename)));
        } catch(FileNotFoundException e) {
            throw new IllegalArgumentException("FILE DOES NOT EXIST");
        }
    }

    public Boolean call() {
        ExecutorService reporters = Executors.newFixedThreadPool(patterns.size());

        for (String pattern : patterns) {
            reporters.submit(new ReportMatches(pattern, mediator));
        }

        ExecutorService readers = Executors.newFixedThreadPool(filenames.size());

        for (String filename : filenames) {
            readers.submit(
                    new ReadFile(filename, readerForFilename(filename), mediator));
        }

        try {
            readers.shutdown();
            readers.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            mediator.shutdown();

            reporters.shutdown();
            reporters.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return true;
    }
}

package im.fuad.rit.concpar.p1;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import im.fuad.rit.concpar.p1.ReadFile;
import im.fuad.rit.concpar.p1.ReportMatches;

class ParallelGrep implements Callable<Boolean> {
    public static final String END_SIGNAL = "END_SIGNAL";

    private List<String> filenames;
    private BlockingQueue<String> words;

    public ParallelGrep(List<String> filenames) {
        this.filenames = filenames;
        this.words = new LinkedBlockingQueue<String>();
    }

    public Boolean call() {
        List<Thread> readers = new ArrayList<Thread>();

        try {
            for (String filename : filenames) {
                Thread t =
                    new Thread(
                            new ReadFile(
                                new BufferedReader(
                                    new FileReader(
                                        new File(filename))), words));

                readers.add(t);

                t.start();
            }

            for (Integer i = 0; i < REPORTERS_COUNT; i++) {

            }

            Thread reporter = new Thread(new ReportMatches(words));

            reporter.start();

            try {
                for (Thread reader : readers) reader.join();

                words.put(END_SIGNAL);

                reporter.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            return true;
        } catch (FileNotFoundException e) {
                e.printStackTrace();

            return false;
        }
    }
}

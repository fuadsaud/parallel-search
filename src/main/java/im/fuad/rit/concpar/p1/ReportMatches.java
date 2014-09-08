package im.fuad.rit.concpar.p1;

import java.util.concurrent.BlockingQueue;

class ReportMatches implements Runnable {
    private BlockingQueue<String> matches;

    public ReportMatches(BlockingQueue<String> matches) {
        this.matches = matches;
    }

    public void run() {
        while (true) {
            try {
                String word = matches.take();

                if (word == ParallelGrep.END_SIGNAL) {
                    matches.add(word);

                    return;
                }

                System.out.println("deq: " + word);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

package im.fuad.rit.concpar.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

class ReadFile implements Runnable {
    private BufferedReader input;
    private Queue<String> toBeProcessed;
    private HashMap<String, Boolean> words;

    public ReadFile(BufferedReader input, Queue<String> toBeProcessed) {
        this.input = input;
        this.toBeProcessed = toBeProcessed;
        this.words = new HashMap<String, Boolean>();
    }

    public void run() {
        String line;
        List<String> lineWords;

        try {
            while ((line = input.readLine()) != null) {
                if (line.isEmpty()) continue;

                lineWords = new ArrayList<String>(Arrays.asList(line.split(" ")));

                for (String word : lineWords) {
                    if (words.containsKey(word)) {
                        toBeProcessed.add(word);

                        System.out.println("enq: " + word);
                    }

                    words.put(word, true);
                }
            }
        } catch (IOException e) {
            System.out.println("BOOM");
        }
    }
}

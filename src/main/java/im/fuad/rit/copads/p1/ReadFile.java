package im.fuad.rit.copads.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.Callable;

import im.fuad.rit.copads.p1.WordOccurrence;
import im.fuad.rit.copads.p1.Mediator;

/**
 * This class defines a task for reading files, extracting words from it and
 * submitting these words to a given mediator object. Words are submitted only
 * once, even if multiple ocurrences are found.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class ReadFile implements Callable<Boolean> {
    private final String WORDS_SEPARATOR = "[^a-zA-Z]";

    private String filename;
    private BufferedReader input;
    private Mediator mediator;
    private HashMap<String, Boolean> words;

    public ReadFile(String filename, BufferedReader input, Mediator mediator) {
        this.filename = filename;
        this.input = input;
        this.mediator = mediator;
        this.words = new HashMap<String, Boolean>();
    }

    public Boolean call() {
        String line;
        List<String> lineWords;

        try {
            while ((line = input.readLine()) != null) {
                if (line.isEmpty()) continue;

                lineWords = Arrays.asList(line.split(WORDS_SEPARATOR));

                for (String word : lineWords) {
                    String key = word.toLowerCase();

                    if (!words.containsKey(key)) {
                        mediator.put(new WordOccurrence(word, this.filename));
                    }

                    words.put(key, true);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }
}

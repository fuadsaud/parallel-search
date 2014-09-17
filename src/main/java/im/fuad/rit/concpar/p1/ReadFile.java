package im.fuad.rit.concpar.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.Callable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.fuad.rit.concpar.p1.WordOccurrence;
import im.fuad.rit.concpar.p1.Mediator;

/**
 * This class defines a task for reading files, extracting words from it and
 * submitting these words to a given mediator object. Words are submitted only
 * once, even if multiple ocurrences are found.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
class ReadFile implements Callable<Boolean> {
    private final Pattern WORDS_PATTERN = Pattern.compile("\\w+");

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

                lineWords = new ArrayList<String>(Arrays.asList(line.split(" ")));

                // lineWords = new ArrayList<String>();
                // Matcher matcher = WORDS_PATTERN.matcher(line);

                // if (matcher.find()) {
                //     for (int i = 0; i < matcher.groupCount(); i++) {
                //         lineWords.add(matcher.group(i));

                //         System.out.println("SDFHIDSHF: " + matcher.group(i));
                //     }
                // }

                for (String word : lineWords) {
                    if (!words.containsKey(word)) {
                        mediator.put(new WordOccurrence(word, this.filename));
                    }

                    words.put(word, true);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }
}

import java.util.Arrays;
import java.util.List;

import im.fuad.rit.concpar.p1.ParallelSearch;

class Search {
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);

        if (arguments.size() != 2) {
            usage();

            return;
        }

        new ParallelSearch(arguments).call();
    }

    public static void usage() {
        System.out.println("USAGE:");
        System.out.println("java Search <FILELIST> <WORDLIST>");
        System.out.println("<FILELIST>: list of comma separated file paths to be searched");
        System.out.println("<WORDLIST>: list of comma separated words to be searched for");
    }
}

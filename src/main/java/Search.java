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
        System.out.println("\tjava Search <FILELIST> <WORDLIST>");
        System.out.println("\t\t<FILELIST>: list of comma separated file paths to be searched");
        System.out.println("\t\t<WORDLIST>: list of comma separated words to be searched for");
        System.out.println("\n\t\tExample: java Search file1,../file2 word1,word2");
    }
}

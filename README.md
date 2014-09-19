# Parallel Search

## Usage


```sh
  java Search <FILELIST> <WORDLIST>
    # <FILELIST>: list of comma separated file paths to be searched
    # <WORDLIST>: list of comma separated words to be searched for

    Example: java Search file1,../file2 word1,word2
```

## Build

Running `make classes` will compile everything and throw it under the `bin/`
directory. `cd`'ing there you can run the aforementioned command to start the
program.

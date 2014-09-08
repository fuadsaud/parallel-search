package im.fuad.rit.concpar.p1;

import java.util.Arrays;

import im.fuad.rit.concpar.p1.ParallelGrep;

class P1 {
    public static void main(String[] args) {
        new ParallelGrep(Arrays.asList(args)).call();
    }
}

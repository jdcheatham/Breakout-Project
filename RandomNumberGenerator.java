package com.jamescho.framework.util;

import java.util.Random;

/**
 * Created by jjcoy on 10/3/16.
 */
public class RandomNumberGenerator {

    private static Random rand = new Random();      // one random number generator for game

    public static int getRandIntBetween(int lowerBound, int upperBound) {
        // nextInt returns a number between 0 and what is passed to it (but not including that number)
        // if we want a number between 5 - 10 (not including 10), we generate a
        // random number between 0 and 5, then add 5 to it to shift it up into the range
        // we want
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }
}

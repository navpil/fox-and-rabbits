package io.github.navpil.foxandrabbits;

import java.util.Random;

/**
 * Provide control over the randomization of the simulation.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Randomizer
{
    // The default seed for control of randomization.
    private static final int FOXES_WIN = 1111;
    private static final int BEARS_WIN = 1112;
    // A shared Random object, if required.
    private static final Random rand = new Random(FOXES_WIN);
    private static final Random randomized = new Random();
    // Determine whether a shared random generator is to be provided.
    private static RandomType type = RandomType.FIXED;

    public static void setType(RandomType type) {
        Randomizer.type = type;
    }

    /**
     * Constructor for objects of class Randomizer
     */
    public Randomizer()
    {
    }

    /**
     * Provide a random generator.
     * @return A random object.
     */
    public static Random getRandom()
    {
        switch (type) {
            case FIXED: return rand;
            case SHARED: return randomized;
            default: return new Random();
        }
    }

    enum RandomType {
        FIXED, SHARED, NON_SHARED
    }

}

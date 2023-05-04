package io.github.navpil.foxandrabbits.animals;

public class AnimalParameters {

    public final int MAX_AGE;
    public final int BREEDING_AGE;
    public final double BREEDING_PROBABILITY;
    public final int MAX_LITTER_SIZE;
    public final int MAX_FOOD_VALUE;
    public final int size;
    public final boolean neverHungry;

    public AnimalParameters(int MAX_AGE, int BREEDING_AGE, double BREEDING_PROBABILITY,
                            int MAX_LITTER_SIZE, int MAX_FOOD_VALUE, int size,
                            boolean neverHungry) {
        this.MAX_AGE = MAX_AGE;
        this.BREEDING_AGE = BREEDING_AGE;
        this.BREEDING_PROBABILITY = BREEDING_PROBABILITY;
        this.MAX_LITTER_SIZE = MAX_LITTER_SIZE;
        this.MAX_FOOD_VALUE = MAX_FOOD_VALUE;
        this.size = size;
        this.neverHungry = neverHungry;
    }
}

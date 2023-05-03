package io.github.navpil.animals;

import io.github.navpil.Field;
import io.github.navpil.Location;
import io.github.navpil.Randomizer;

import java.awt.*;
import java.util.Random;

public enum AnimalRegistryLongLiveBears {

    INSTANCE;

    private AnimalParameters FOX = new AnimalParameters(
            150, 15,
            0.08, 4, 9,
            18, false
    );

    private AnimalParameters WOLF = new AnimalParameters(
            150, 25,
            0.08, 4, 13,
            25, false
    );

    private AnimalParameters BEAR = new AnimalParameters(
            190,
            30,
            0.02,
            1,
            90,
            60,
            false

    );

    private AnimalParameters BIG_RABBIT = new AnimalParameters(
            40, 6,
            0.12,
            5, 100 /* not used */,
            16,
            true

    );
    private AnimalParameters SMALL_RABBIT = new AnimalParameters(
            40, 5,
            0.12,
            4, 100 /* not used */,
            9, true

    );

    // The probability that a bear will be created in any given grid position.
    private static final double BEAR_CREATION_PROBABILITY = 0.01;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.04;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.06;

    public Animal randomAnimal(Field field, int row, int col) {
        Random rand = Randomizer.getRandom();
        if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
            Location location = new Location(row, col);
            return new DefaultAnimal(field, location, SMALL_RABBIT, "Rabbit", Color.yellow).randomize();
        }
//        if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
//            Location location = new Location(row, col);
//            return new DefaultAnimal(field, location, BIG_RABBIT, "BigRabbit", Color.orange).randomize();
//        }
        if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
            Location location = new Location(row, col);
            return new DefaultAnimal(field, location, FOX, "Fox", Color.blue).randomize();
        }
//
//        if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
//            Location location = new Location(row, col);
//            return new DefaultAnimal(field, location, WOLF, "Wolf", Color.gray).randomize();
//        }
        if(rand.nextDouble() <= BEAR_CREATION_PROBABILITY) {
            Location location = new Location(row, col);
            return new DefaultAnimal(field, location, BEAR, "Bear",Color.red).randomize();
        }
        return null;

    }
}

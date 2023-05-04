package io.github.navpil.animals;

import io.github.navpil.Field;
import io.github.navpil.Location;
import io.github.navpil.Randomizer;

import java.awt.*;
import java.util.Random;

public enum MutantRabbits implements AnimalFactory {

    INSTANCE;

    private AnimalParameters FOX = new AnimalParameters(
            150, 15,
            0.01, 4, 9,
            26, false
    );

    private AnimalParameters BEAR = new AnimalParameters(
            150, 25,
            0.01, 4, 100,
            60, false
    );

    private AnimalParameters SMALL_RABBIT = new AnimalParameters(
            40, 5,
            0.12,
            4, 4,
            9, false

    );

    public Animal randomAnimal(Field field, int row, int col) {
        Random rand = Randomizer.getRandom();
        if(rand.nextDouble() <= 0.06) {
            Location location = new Location(row, col);
            return new MutantAnimal(field, location, SMALL_RABBIT, "Rabbit", Color.red).randomize();
        }
//        if(rand.nextDouble() <= 0.02) {
//            Location location = new Location(row, col);
//            return new Devourer(field, location, BEAR, "Bear", Color.blue).randomize();
//        }
        if(rand.nextDouble() <= 0.02) {
            Location location = new Location(row, col);
            return new Devourer(field, location, BEAR, "Bear", Color.blue).randomize();
        }
        return null;

    }
}

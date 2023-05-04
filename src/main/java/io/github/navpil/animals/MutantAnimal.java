package io.github.navpil.animals;

import io.github.navpil.Field;
import io.github.navpil.Location;
import io.github.navpil.Randomizer;

import java.awt.*;
import java.util.Random;

public class MutantAnimal extends DefaultAnimal {

    private final AnimalParameters animalParameters;
    private final String name;
    private final Color color;

    public MutantAnimal(Field field, Location location, AnimalParameters animalParameters, String name, Color color) {
        super(field, location, animalParameters, name, color);
        this.animalParameters = animalParameters;
        this.name = name;
        this.color = color;
    }



    @Override
    protected Animal createChild(Field field, Location loc) {
        Random random = Randomizer.getRandom();
        int size;
        double breedingProbability;
        if (random.nextBoolean()) {
            size = animalParameters.size + 1;
            breedingProbability = animalParameters.BREEDING_PROBABILITY / 1.01;

        } else {
            size = Math.max(2, animalParameters.size - 1);
            breedingProbability = animalParameters.BREEDING_PROBABILITY * 1.01;
        }
        Color color1;
        if (size < 10) {
            color1 = Color.yellow;
        } else if (size < 23) {
            color1 = Color.orange;
        } else if (size < 50) {
            color1 = Color.red;
        } else if (size < 75) {
            color1 = Color.blue;
        } else {
            color1 = Color.black;
        }

        AnimalParameters animalParameters1 = new AnimalParameters(
                animalParameters.MAX_AGE,
                animalParameters.BREEDING_AGE,
                breedingProbability,
                animalParameters.MAX_LITTER_SIZE,
                animalParameters.BREEDING_AGE - 1,
                size,
                false
        );
        return new MutantAnimal(field, loc, animalParameters1, name, color1);
    }
}

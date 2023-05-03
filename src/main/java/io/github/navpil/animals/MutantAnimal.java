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
        Color color1;
        double breedingProbability;
        if (random.nextBoolean()) {
            size = animalParameters.size + 1;
            color1 = new Color(Math.max(0 ,color.getRed() - 20), 0, 125);
            breedingProbability = animalParameters.BREEDING_PROBABILITY / 1.01;

        } else {
            size = Math.max(2, animalParameters.size - 1);
            color1 = new Color(Math.min(255, color.getRed() + 20), 0, 0);
            breedingProbability = animalParameters.BREEDING_PROBABILITY * 1.01;
        }
        AnimalParameters animalParameters1 = new AnimalParameters(
                animalParameters.MAX_AGE,
                animalParameters.BREEDING_AGE,
                breedingProbability,
                animalParameters.MAX_LITTER_SIZE,
                animalParameters.MAX_FOOD_VALUE,
                size,
                false
        );
        return new MutantAnimal(field, loc, animalParameters1, name, color1);
    }
}

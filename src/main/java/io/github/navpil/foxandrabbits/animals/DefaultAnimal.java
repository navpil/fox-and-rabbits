package io.github.navpil.foxandrabbits.animals;

import io.github.navpil.foxandrabbits.Field;
import io.github.navpil.foxandrabbits.Location;

import java.awt.*;

public class DefaultAnimal extends Animal {


    private final String name;
    private final AnimalParameters animalParameters;
    private final Color color;

    public DefaultAnimal(Field field, Location location,
                         AnimalParameters animalParameters,
                         String name, Color color) {
        super(field, location,
                animalParameters.MAX_AGE,
                animalParameters.BREEDING_AGE, animalParameters.BREEDING_PROBABILITY,
                animalParameters.MAX_LITTER_SIZE, animalParameters.MAX_FOOD_VALUE,
                animalParameters.size);
        this.animalParameters = animalParameters;
        this.name = name;
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    protected Animal createChild(Field field, Location loc) {
        return new DefaultAnimal(field, loc, animalParameters, name, color);
    }

    @Override
    protected void incrementHunger() {
        if (animalParameters.neverHungry) {
            return;
        }
        if (animalParameters.size > 10) {
            super.incrementHunger();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return animalParameters.size;
    }
}

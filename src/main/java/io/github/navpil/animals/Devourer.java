package io.github.navpil.animals;

import io.github.navpil.Field;
import io.github.navpil.Location;

import java.awt.*;

public class Devourer extends DefaultAnimal {
    public Devourer(Field field, Location location, AnimalParameters animalParameters, String name, Color color) {
        super(field, location, animalParameters, name, color);
    }

    @Override
    protected boolean canEat(Animal animal) {
        return animal != null && getSize() > (animal.getSize() * 2);
    }
}

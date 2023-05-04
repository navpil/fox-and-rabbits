package io.github.navpil.foxandrabbits.animals;

import io.github.navpil.foxandrabbits.Field;

public interface AnimalFactory {

    Animal randomAnimal(Field field, int row, int col);
}

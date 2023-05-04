package io.github.navpil.animals;

import io.github.navpil.Field;

public interface AnimalFactory {

    Animal randomAnimal(Field field, int row, int col);
}

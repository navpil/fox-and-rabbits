package io.github.navpil.animals;

import io.github.navpil.Field;

public enum AnimalRegistry {

    INSTANCE;

    public Animal randomAnimal(Field field, int row, int col) {
        return AnimalRegistryMutantRabbits.INSTANCE.randomAnimal(field, row, col);
    }
}

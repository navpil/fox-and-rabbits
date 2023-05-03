package io.github.navpil.animals;

import io.github.navpil.Field;
import io.github.navpil.Location;
import io.github.navpil.Randomizer;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal implements Actor
{

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    protected final int MAX_AGE;
    private final int size;
    protected int foodLevel;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // Individual characteristics (instance fields).
    // The fox's age.
    private int age;
    private int BREEDING_AGE;
    private double BREEDING_PROBABILITY;
    private int MAX_LITTER_SIZE;
    private int MAX_FOOD_VALUE;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     * @param MAX_AGE
     */
    public Animal(Field field, Location location, int MAX_AGE, int BREEDING_AGE,
                  double BREEDING_PROBABILITY, int MAX_LITTER_SIZE, int MAX_FOOD_VALUE, int size)
    {
        this.BREEDING_PROBABILITY = BREEDING_PROBABILITY;
        this.MAX_LITTER_SIZE = MAX_LITTER_SIZE;
        this.MAX_FOOD_VALUE = MAX_FOOD_VALUE;
        alive = true;
        this.field = field;
        this.MAX_AGE = MAX_AGE;
        this.BREEDING_AGE = BREEDING_AGE;
        foodLevel = MAX_FOOD_VALUE;
        this.size = size;
        setLocation(location);
    }

    public abstract Color getColor();

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Actor> newFoxes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }


    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Actor> newFoxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createChild(field, loc);
            newFoxes.add(young);
        }
    }

    abstract protected Animal createChild(Field field, Location loc);


    /**
     * Look for foxes and rabbits adjacent to the current location.
     * Only the first live fox or rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Animal animal = field.getObjectAt(where);
            if (canEat(animal)) {
                animal.setDead();
                foodLevel = Math.min(foodLevel + animal.getFoodValue(), MAX_FOOD_VALUE);
                return where;
            }
        }
        return null;
    }

    public int getFoodValue() {
        return size;
    }

    protected boolean canEat(Animal animal) {
        return animal != null && animal.isAlive() &&
                this.size / 2 >= animal.size &&
                this.size / 4 <= animal.size;
    }

    public Animal randomize() {
        age = rand.nextInt(MAX_AGE);
        foodLevel = rand.nextInt(MAX_FOOD_VALUE);
        return this;
    }

    /**
     * Increase the age. This could result in the bear's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this bear more hungry. This could result in the bear's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Field getField()
    {
        return field;
    }


    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    public abstract String getName();

    public abstract int getSize();
}

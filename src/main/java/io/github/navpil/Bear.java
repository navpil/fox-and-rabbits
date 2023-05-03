package io.github.navpil;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a Bear.
 * Bear age, move, eat foxes and rabbits, and die.
 * 
 * @author Manjinder Singh
 */
public class Bear extends Animal
{
    // Characteristics shared by all bears (class variables).

    // The food value of a single rabbit and a single fox. In effect, this is the
    // number of steps a bear can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    private static final int FOX_FOOD_VALUE = 18;
    // A shared random number generator to control breeding
    private static final Random rand = Randomizer.getRandom();

    // The bear's food level, which is increased by eating foxes and rabbits
    private int foodLevel;

    /**
     * Create a bear. A bear can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bear(Field field, Location location)
    {
        super(field, location,
                190,
                30,
                0.02,
                1);
        foodLevel = RABBIT_FOOD_VALUE + FOX_FOOD_VALUE;
    }

    @Override
    public Bear randomize() {
        age = rand.nextInt(MAX_AGE);
        foodLevel = rand.nextInt(RABBIT_FOOD_VALUE + FOX_FOOD_VALUE);
        return this;
    }

    /**
     * This is what the bear does most of the time: it hunts for
     * foxes and rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newBears A list to return newly born bears.
     */    
    public void act(List<Actor> newBears)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newBears);            
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
     * Increase the age. This could result in the bear's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this bear more hungry. This could result in the bear's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for foxes and rabbits adjacent to the current location.
     * Only the first live fox or rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) 
            {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) 
                { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;//Math.min(foodLevel + RABBIT_FOOD_VALUE, FOX_FOOD_VALUE);
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
            else if (animal instanceof Fox)
            {
                Fox fox = (Fox) animal;
                if(fox.isAlive())
                {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    // Remove the dead fox from the field.
                    return where;
                }

            }
        }
        return null;
    }
    
    /**
     * Check whether or not this bear is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newBears A list to return newly born bears.
     */
    private void giveBirth(List<Actor> newBears)
    {
        // New bears are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bear young = new Bear(field, loc);
            newBears.add(young);
        }
    }
    
}

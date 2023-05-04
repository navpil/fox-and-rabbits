package io.github.navpil.foxandrabbits;

import io.github.navpil.foxandrabbits.animals.Animal;

import java.util.HashMap;

/**
 * This class collects and provides some statistical data on the state 
 * of a field. It is flexible: it will create and maintain a counter 
 * for any class of object that is found within the field.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
@SuppressWarnings("all")
public class FieldStats
{

    // Counters for each type of entity (fox, rabbit, etc.) in the simulation.
    private HashMap<String, Counter> counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a FieldStats object.
     */
    public FieldStats()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap<String, Counter>();
        countsValid = true;
    }

    /**
     * Get details of what is in the field.
     * @return A string describing what is in the field.
     */
    public String getPopulationDetails(Field field)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        for(String key : counters.keySet()) {
            Counter info = counters.get(key);
            if("Fox".equals(info.getName())) {
                buffer.append(info.getName() + "(Blue)");
            } else if ("Rabbit".equals(info.getName())) {
                buffer.append(info.getName() + "(Orange)");
            } else if ("Bear".equals(info.getName())) {
                buffer.append(info.getName() + "(Red)");
            } else {
                buffer.append(info.getName());
            }
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalidate the current set of statistics; reset all 
     * counts to zero.
     */
    public void reset()
    {
        countsValid = false;
        for(String key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }

    /**
     * Increment the count for one class of animal.
     * @param animalName The class of animal to increment.
     */
    public void incrementCount(String animalName)
    {
        Counter count = counters.get(animalName);
        if(count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Counter(animalName);
            counters.put(animalName, count);
        }
        count.increment();
    }

    /**
     * Indicate that an animal count has been completed.
     */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determine whether the simulation is still viable.
     * I.e., should it continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        if (true) return true;
        // How many counts are non-zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(String key : counters.keySet()) {
            Counter info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Generate counts of the number of foxes and rabbits.
     * These are not kept up to date as foxes and rabbits
     * are placed in the field, but only when a request
     * is made for the information.
     * @param field The field to generate the stats for.
     */
    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Animal animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getName());
                }
            }
        }
        countsValid = true;
    }
}

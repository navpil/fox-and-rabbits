package io.github.navpil;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a hunter.
 * Hunter hunt foxes, bears and rabbits.
 * 
 * @author Manjinder Singh
 */
public class Hunter implements Actor
{
    // Characteristics shared by all hunters (class variables).
	
    // The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;
    // Whether the hunter has been arrested or not.
    private boolean arrested;
    
    /**
     * Create a new hunter at location in field.
     *  
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location newLocation)
    {
        arrested = false;
        this.field = field;
        setLocation(newLocation);
    }

    /**
     * This is what the hunter does most of the time: it hunts for
     * foxes, rabbits and bears.
     * @param field The field currently occupied.
     * @param newbears A list to return newly Hunters.
     */
    public void act(List<Actor> newHunters)
    {
        if(! isNotArrested()) {
	    	// Move towards a source of food if found.
	        Location newLocation = findAnimal();
	        if(newLocation == null) { 
	            // No food found - try to move to a free location.
	            newLocation = getField().freeAdjacentLocation(getLocation());
	        }
	        // See if it was possible to move.
	        if(newLocation != null) {
	            setLocation(newLocation);
	        }
	        else {
	        	// OverCrowding
	        	setArrested();
	        }
        } 
    }


    /**
     * Look for an animal adjacent to the current location.
     * Only the first live animal is shoot.
     * @return Where an animal was found, or null if it wasn't.
     */
    private Location findAnimal()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Fox)
            {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) 
                { 
                    fox.setDead();
                    return where;
                }
            	
            }
            else if(animal instanceof Rabbit) 
            {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) 
                { 
                    rabbit.setDead();
                    return where;
                }
            }
            else if (animal instanceof Bear)
            {
                Bear bear = (Bear) animal;
                if(bear.isAlive()) 
                { 
                    bear.setDead();
                    return where;
                }
            	
            }
        }
        return null;
    }
    
    /**
     * Return the hunter's location.
     * @return The hunter's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
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
     * Return the hunter's field.
     * @return Field the hunter's field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Indicate that the hunter has been arrested.
     * It is removed from the field.
     */
    protected void setArrested()
    {
        arrested = true;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Check whether the hunter has been arrested or not.
     * @return true if the hunter has not been arrested.
     */
	public boolean isNotArrested() {
		return arrested;
	}  
}
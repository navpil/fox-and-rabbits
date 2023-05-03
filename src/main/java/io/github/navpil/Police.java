package io.github.navpil;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a Policeman.
 * Policemen arrest Hunters since it is illegal to hunt.
 * 
 * @author Manjinder Singh
 */
public class Police implements Actor
{
    // Characteristics shared by all policemen (class variables).
	
    // The policeman's field.
    private Field field;
    // The policeman's position in the field.
    private Location location;
    
    /**
     * Create a new policeman at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Police(Field field, Location newLocation)
    {
        this.field = field;
        setLocation(newLocation);
    }

    /**
     * This is what the policeman does most of the time: it arrests
     * hunters.
     * @param field The field currently occupied.
     * @param newPolicemen A list to return newly born bears.
     */
    
    public void act(List<Actor> newPolicemen)
    {
    	// Move towards a source of hunter if found.
        Location newLocation = findHunter();
        if(newLocation == null) { 
            // No hunter found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }

    }


    /**
     * Look for a hunter adjacent to the current location.
     * Only the first live hunter is arrested.
     * @return Where a hunter was found, or null if it wasn't.
     */
    private Location findHunter()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where); 
            if(actor instanceof Hunter) 
            {
                Hunter hunter = (Hunter) actor;
                if(!hunter.isNotArrested()) {
                	hunter.setArrested();
                	return where;
                }
            }
            
        }
        return null;
    }
    
    /**
     * Return the policeman's location.
     * @return The Policeman's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the Policeman at the new location in the given field.
     * @param newLocation The Policeman's new location.
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
     * Return the policeman's field.
     * @return Field the Policeman's field.
     */
    public Field getField()
    {
        return field;
    }   
}
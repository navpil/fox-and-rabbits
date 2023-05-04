package io.github.navpil.foxandrabbits.animals;

import io.github.navpil.foxandrabbits.Location;

import java.util.List;

/**
 * A class representing shared characteristics of actors
 * @author Manjinder Singh
 *
 */

public interface Actor {

	/**
     * Make this actor act - that is: make it do
     * whatever it wants/needs to do.
     * @param newActors A list to receive new Actors.
     */
	void act(List<Actor> newActors);
    
    /**
     * Place the actor at the new location in the given field.
     * @param newLocation The actor's new location.
     */
	void setLocation(Location newLocation);
}

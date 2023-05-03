package io.github.navpil;

import io.github.navpil.animals.Actor;
import io.github.navpil.animals.Animal;
import io.github.navpil.animals.AnimalRegistry;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits, foxes, bears, hunters and policemen.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
@SuppressWarnings("all")
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;


    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // A graphical view of the controller.
    private ControllerView controller;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {    	    	
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

        controller = new ControllerView();
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
    */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
    	for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn actors.
        List<Actor> newactors = new ArrayList<Actor>();        
        // Let all actors act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newactors);
            if (actor instanceof Animal) // Check if actor is an animal
	        {
	        	Animal animal = (Animal) actor;
	        	if(! animal.isAlive()) {
	                it.remove();
	            }
	        }
        }
               
        // Add the newly born actors to the main lists.
        actors.addAll(newactors);

        view.showStatus(step, field);
    }
    
    /**
     * Return the simulator view
     * @return view of type SimulatorView 
     */
    public SimulatorView getSimulatorView()
    {
    	return view;
    }
    
    /**
     * Return the controller view
     * @return view of type ControllerView 
     */
    public ControllerView getControllerView() 
    {
    	return controller;
    }
    
    /**
     * Return the field
     * @return field of type Field
     */
    public Field getField()
    {
    	return field;
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Animal animal = AnimalRegistry.INSTANCE.randomAnimal(field, row, col);
                if (animal != null) {
                    actors.add(animal);
                }
            }
        }
    }
}

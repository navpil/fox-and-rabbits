package io.github.navpil;

import io.github.navpil.animals.Animal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
@SuppressWarnings("all")
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;   
    
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {	
        stats = new FieldStats();

        setTitle("Bear, Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
                
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);
         
        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        
        setIconImage(new ImageIcon("icons/fox-icon.png").getImage());  
        pack();
        setVisible(true);
    }


    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Animal animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getName());
                    fieldView.drawMark(col, row, animal.getColor());
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

//        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        population.setText(POPULATION_PREFIX + getPopulationDetails(field));
        fieldView.repaint();
    }

    private String getPopulationDetails(Field field) {
        Collection<Animal> allAnimals = getAllAnimals(field);
        int foxesCounter = 0;
        int smallCounter = 0;
        int mediumCounter = 0;
        int largeCounter = 0;
        int extraLargeCounter = 0;

        TreeMap<String, Counter> map = new TreeMap<>();

        int max = 0;
        int min = Integer.MAX_VALUE;
        for (Animal animal : allAnimals) {
            map.putIfAbsent(animal.getName(), new Counter(animal.getName()));
            map.get(animal.getName()).increment();

            int size = animal.getSize();
            if (size <= 10) {
                smallCounter++;
            } else if (size <= 25) {
                mediumCounter++;
            } else if (size <= 50) {
                largeCounter++;
            } else {
                extraLargeCounter++;
            }
            max = Math.max(max, size);
            min = Math.min(min, size);
        }
        StringBuilder result = new StringBuilder();
        for (String animalName : map.keySet()) {
            result.append(animalName).append(":").append(map.get(animalName).getCount()).append("; ");
        }
        result.append(smallCounter + "/" + mediumCounter + "/" + largeCounter + "/" + extraLargeCounter +", max: " + max + ", min: " + min);
        return result.toString();
    }

    private Collection<Animal> getAllAnimals(Field field) {
        ArrayList<Animal> animals = new ArrayList<>();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Animal animal = field.getObjectAt(row, col);
                if (animal != null) {
                    animals.add(animal);
                }
            }
        }
        return animals;
    }
    
    

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Returns the step label
     * @return step label
     */
    public JLabel getStepLabel ()
    {
    	return stepLabel;
    }
    
    /**
     * Returns the population label
     * @return population label
     */
    public JLabel getPopulationLabel ()
    {
    	return population;
    }
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}

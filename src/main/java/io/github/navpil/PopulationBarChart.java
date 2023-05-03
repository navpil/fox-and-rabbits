package io.github.navpil;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
/**
 * Class for showing detailed analytics of the population
 * 
 * @author Manjinder Singh
 *
 */
public class PopulationBarChart extends Application {
	
	
	// ArrayList for storing data coming from Reader
	private ArrayList<ArrayList<Integer>> data;
	private Reader info;
	private ArrayList<Integer> steps;
	private ArrayList<Integer> rabbits;
	private ArrayList<Integer> bears; 
	private ArrayList<Integer> hunters;
	private ArrayList<Integer> foxes;
	private ArrayList<Integer> police;
	
	public PopulationBarChart() {
		data = new ArrayList<>();
		info = new Reader();
		data = info.read();
		
		// Declaration Variables needed to store population details
		steps = data.get(0);
		rabbits = data.get(1);
		bears = data.get(2);
		hunters = data.get(3);
		foxes = data.get(4);
		police = data.get(5);
	}
		
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@SuppressWarnings("unchecked")
	@Override public void start(Stage stage) {	
	    	
        	// Set title of the window
	    	stage.setTitle("Fox, Rabbits, Bears, Hunters and Police Population");
	    	// Declare xAxis and yAxis
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        // Creation of the BarChart
	        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
	        // Settings of title and label
	        bc.setTitle("Population through the various steps");
	        xAxis.setLabel("Steps");       
	        yAxis.setLabel("Population");
	 
	        // Series Declaration with the steps along the population of the Foxes
	        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
			series1.setName("Foxes");    
			addingLoop(series1);
			// Series Declaration with the steps along the population of the Rabbits
			XYChart.Series<String, Number> series2 = new XYChart.Series<>();
			series2.setName("Bears");
			addingLoop(series2);
			// Series Declaration with the steps along the population of the Bears
			XYChart.Series<String, Number> series3 = new XYChart.Series<>();
			series3.setName("Rabbits");
			addingLoop(series3);
			// Series Declaration with the steps along the population of the Hunters
			XYChart.Series<String, Number> series4 = new XYChart.Series<>();
			series4.setName("Hunters");
			addingLoop(series4);
			// Series Declaration with the steps along the population of the Police
			XYChart.Series<String, Number> series5 = new XYChart.Series<>();
			series5.setName("Police");
			addingLoop(series5);
	        Scene scene  = new Scene(bc,800,600);
			bc.getData().addAll(series1, series2, series3, series4, series5);
	        stage.setScene(scene);
	        stage.show();
    }
	
	/**
	 * Function for adding the data into the BarChart
	 * @param series
	 */
	public void addingLoop(XYChart.Series<String, Number> series) {
		for (int i=0; i<steps.size(); i++) {
			if(series.getName() == "Foxes") {
				series.getData().add(new XYChart.Data<String, Number>("Steps: " + steps.get(i), foxes.get(i)));
			} else if(series.getName() == "Bears") {
				series.getData().add(new XYChart.Data<String, Number>("Steps: " + steps.get(i), bears.get(i)));
			} else if(series.getName() == "Rabbits") {
				series.getData().add(new XYChart.Data<String, Number>("Steps: " + steps.get(i), rabbits.get(i)));
			} else if(series.getName() == "Hunters") {
				series.getData().add(new XYChart.Data<String, Number>("Steps: " + steps.get(i), hunters.get(i)));
			} else if(series.getName() == "Police") {
				series.getData().add(new XYChart.Data<String, Number>("Steps: " + steps.get(i), police.get(i)));
			}
		}
	}

    /*
     * Function for Launching JAVAFX Application through "launch" method
     */
    public void createTable() {
    	try {
        	launch();
    	}
    	catch (IllegalStateException e) {
    		JOptionPane.showMessageDialog(null, "You cannot call the application more than once. Please restart the program");
    	}
    }
}
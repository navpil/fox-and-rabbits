package io.github.navpil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Class for reading from the file where population details has been stored.
 * 
 * @author Manjinder Singh
 *
 */
public class Reader {

	// Variables Declaration
	private ArrayList<Integer> steps;
	private ArrayList<Integer> foxes;
	private ArrayList<Integer> rabbits;
	private ArrayList<Integer> bears;
	private ArrayList<Integer> hunters;
	private ArrayList<Integer> police;
	private ArrayList<ArrayList<Integer>> data;
	private ArrayList<Integer> numbers;
	
	/*
	 * Constructor
	 */
	public Reader() {
		steps = new ArrayList<>();
		foxes = new ArrayList<>();
		rabbits = new ArrayList<>();
		bears = new ArrayList<>();
		hunters = new ArrayList<>();
		police = new ArrayList<>();
		numbers = new ArrayList<>();
		data = new ArrayList<>();
	}
		
	/**
	 * Function for reading from the .txt file.
	 * 
	 * @return ArrayList of Integers which includes the population data of all the characters used.
	 */
	public ArrayList<ArrayList<Integer>> read() {
		try (Scanner reader = new Scanner(new File("population.txt"))) {
			  while (reader.hasNext()) { 
				 // Reading Line by Line
				 String currentLine = reader.nextLine();
				  
		         // Splitting the lines into words
		         String words[] = currentLine.split(" ");

		         // Converts each word into integer
		         for(String str : words) {
		            try {
		               int num = Integer.parseInt(str); 
		               numbers.add(num);			
		            } catch(NumberFormatException nfe) { }; // Checks if the word is not an integer
		         }	       
			  }
		  } catch (FileNotFoundException e) {
			  System.out.println("Problem with accessing the file!");
		  }
	
		// Storing all the numbers in a List
		List<Integer> numbersArray = Collections.unmodifiableList(numbers);
		// Breaking the numbers' list into lists of 4
		List<List<Integer>> parts = chopped(numbersArray, 6);
		// Loop for storing all the population's details in the appropriate ArrayList		
		for (int i = 0; i < parts.size(); i++) {
			steps.add(parts.get(i).get(0));
			rabbits.add(parts.get(i).get(1));	
			bears.add(parts.get(i).get(2));
			hunters.add(parts.get(i).get(3));
			foxes.add(parts.get(i).get(4));
			police.add(parts.get(i).get(5));
		}
		
		// Storing all the ArrayLists in one ArrayList in order to use them in the PopulationBarChart Class
		data.add(steps);
		data.add(rabbits);
		data.add(bears);
		data.add(hunters);
		data.add(foxes);
		data.add(police);
		
		return data;
	}
	
	/*
	 * Function for breaking the List in small Lists.
	 * @param The list to break
	 * @param The number of lists in which has to be broken 
	 */
	public static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}

}

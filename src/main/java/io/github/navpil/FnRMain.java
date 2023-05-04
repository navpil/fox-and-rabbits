package io.github.navpil;

import io.github.navpil.animals.LongLiveBears;
import io.github.navpil.animals.MutantRabbits;

/**
 * Main Class for running the Simulator
 * @author Manjinder Singh
 * 
 */
public class FnRMain {

	// Variable Declaration
	private static Simulator s;
	
	public static void main(String[] args) {
		//MutantRabbits.INSTANCE;
		//LongLiveBears.INSTANCE;
		Randomizer.setType(Randomizer.RandomType.FIXED);
		s = new Simulator(100, 100, MutantRabbits.INSTANCE);
	}
	
	/**
     * Return the simulator
     * @return Simulator
     */
	public static Simulator getSimulator() {
		return s;
	}

}
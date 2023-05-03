package io.github.navpil;

/**
 * Main Class for running the Simulator
 * @author Manjinder Singh
 * 
 */
public class FnRMain {

	// Variable Declaration
	private static Simulator s;
	
	public static void main(String[] args) {
		s = new Simulator(100, 100); 
	}
	
	/**
     * Return the simulator
     * @return Simulator
     */
	public static Simulator getSimulator() {
		return s;
	}

}
package io.github.navpil.foxandrabbits;

/**
 * Thread for managing the simulator View
 * 
 * @author Manjinder Singh
 *
 */
public class ThreadRunner implements Runnable
{	
	// Declaration of Variables
	private int numSteps;
	private boolean infinite;
	private boolean threadRun;
	private int timer;
		
	public ThreadRunner() 
	{
		numSteps = 0;
		infinite = false;
		threadRun = false;
		timer = 0;
	}
	
	/**
     * Function for running the simulation
     * @param numSteps of steps required to run
     */
    public void startRun(int numSteps)
    {
    	if (numSteps == 0)
    	{
    		this.numSteps = 1;
    		infinite = true;
    	}
    	else
    	{
    		this.numSteps += numSteps;
    	}
    	
    	try{
    		if (!threadRun && Thread.currentThread().isAlive())
    		{
    			new Thread(this).start();
    		}
    	} 
    	catch (IllegalThreadStateException e)
    	{
    		infinite = false;
        	System.out.println("Interrupted Exception");
    	}
	}
    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
    	FnRMain.getSimulator().reset();
    }

    /**
     * Pause the animation
     */
	public void pause() 
	{
		numSteps = 0;
		threadRun = false;
		infinite = false;
	}
	
	/**
	 * This method is only performed on the use of the start() method of the Thread class.
	 */
	@Override
	public void run() 
	{
		threadRun = true;
		Simulator simulator = FnRMain.getSimulator();
		timer = simulator.getControllerView().getTimerValue();

		/**
	     * Checks if  whether the simulation should continue to run through isViable(),
	     * with the variables threadRun and numSteps
	     */
		while(threadRun && numSteps > 0 && simulator.getSimulatorView().isViable(simulator.getField()))
		{
			FnRMain.getSimulator().simulateOneStep();
			numSteps--;
			while(infinite && numSteps == 0)
			{
				numSteps++;
			}
			
			/**
			 * Slow Down the simulation
			 */
			try {
				Thread.sleep(timer);
			} 
			catch (Exception e) 
			{
            	System.out.println("InterruptedException");
			}
		}
		threadRun = false;
	}
}

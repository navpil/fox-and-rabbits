package io.github.navpil;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControllerView {
	
	private JFrame f;
	private JPanel panel1, panel2, midPanel;
	private JSplitPane splitPane, splitPane1;
	private JTextField stepsTextField;
	private JButton step, step1, start, pause, reset, save, checkFile, createBarChart, quit;
	private JSlider slider;
    private ThreadRunner threadRunner;   
    private PopulationBarChart barChart;
    private int steps;
    private int timerValue;

	public ControllerView() {
		threadRunner = new ThreadRunner();
    	barChart = new PopulationBarChart();
    	steps = 0;
    	timerValue = 0;
    	
		// New Frame
        f = new JFrame("Controller");
        
        // Panel Which Contains Main Controller Buttons
        panel1 = new JPanel();
        f.add(panel1);
        
        midPanel = new JPanel();
        f.add(midPanel);
        
        // Panel Which Contains File Controller Buttons
        panel2 = new JPanel();
        f.add(panel2);
             
        // Text Field for entering preferred number of steps to run
        stepsTextField = new JTextField("Enter number of steps to run");
        
        // Step Button for stepping by the number entered in the TextField
        step = new JButton("Step");
        step.setIcon(new ImageIcon("icons/step-icon.png"));
        step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					steps = Integer.parseInt(stepsTextField.getText());	
		        	threadRunner.startRun(steps);
				} 
				catch (NumberFormatException exc) {
					JOptionPane.showMessageDialog(f, "Enter a valid number!");
				}
			}
        });
        
        // Step Button for stepping by one
        step1 = new JButton("Step 1");
        step1.setIcon(new ImageIcon("icons/step-icon.png"));
    	step1.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			threadRunner.startRun(1);
    		}
    	});
    	   	    	    	    	
    	// Buttons adding to the Main Controller's Panel
    	panel1.add(stepsTextField);
    	panel1.add(step);
    	panel1.add(step1);
    	
    	// Slider for slowing down the simulation
    	slider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);  
    	slider.setMajorTickSpacing(1);  
    	slider.setPaintTicks(true);  
    	slider.setPaintLabels(true);  
    	slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                    timerValue = slider.getValue();   
            }
        });
    	
    	// Start Button for Starting simulation until the user hits pause
    	start = new JButton("Start");
        start.setIcon(new ImageIcon("icons/start-icon.png"));
    	start.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			threadRunner.startRun(0);
    		}
    	});
    	
    	// Pause Button for Pausing simulation
    	pause = new JButton("Pause");
        pause.setIcon(new ImageIcon("icons/pause-icon.png"));
    	pause.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			threadRunner.pause();
    		}
    	});
    	    	
    	// Reset Button for Resetting Simulation
    	reset = new JButton("Reset");
        reset.setIcon(new ImageIcon("icons/reset-icon.png"));
    	reset.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			threadRunner.reset();
    			slider.setValue(0);
    		}
    	});
    	
    	// Adding buttons to the Middle Panel
    	midPanel.add(slider);
    	midPanel.add(start);
    	midPanel.add(pause);
    	midPanel.add(reset);
    	
    	// Save Button for storing details of the population in .txt file
    	save = new JButton("Save");
        save.setIcon(new ImageIcon("icons/save-icon.png"));
    	save.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			try (FileWriter writer = new FileWriter("population.txt", true);
    					 BufferedWriter bw = new BufferedWriter(writer) ) {
						bw.write(FnRMain.getSimulator().getSimulatorView().getStepLabel().getText() + " --> " + FnRMain.getSimulator().getSimulatorView().getPopulationLabel().getText());
						bw.newLine();
						bw.flush();
					JOptionPane.showMessageDialog(f, "Data stored correctly!");
					
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(f, "Something went wrong with storing the file");
				}
    		}
    	});
    	
    	// Button for opening the .txt file where data is stored
    	checkFile = new JButton("Check Stored Data");
        checkFile.setIcon(new ImageIcon("icons/check-icon.png"));
    	checkFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File("population.txt"));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(f, "File not found!");
				}
    		}
    	});
    	
    	// Button for creating an analysis of a BarChart of the population
    	createBarChart = new JButton("Create Bar Chart");
        createBarChart.setIcon(new ImageIcon("icons/barChart-icon.png"));
    	createBarChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barChart.createTable();
    		}
    	});
    	   	
    	
    	// Button for Quitting from the program
    	quit = new JButton("Quit");
        quit.setIcon(new ImageIcon("icons/quit-icon.png"));
    	quit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			f.dispose();
    			System.exit(0);
    		}
    	});
    	
    	// Adding Button to the File Controller's Panel
    	panel2.add(save);
    	panel2.add(checkFile);
    	panel2.add(createBarChart);
    	panel2.add(quit);
    	
    	// SplitPanel used for splitting the panels in vertical
    	splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  
        panel1, midPanel);    	
    	splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  
    	        splitPane, panel2);

    	// Layout of the Controller's Frame
    	f.getContentPane().add(splitPane1);
        f.setLayout(new FlowLayout(FlowLayout.CENTER));  
        f.setIconImage(new ImageIcon("icons/controller-icon.png").getImage());
        f.setLocation(705,350);
        f.pack();
    	f.setVisible(true);
	}
	
	/**
	 * Return the value of selected slider input
	 * @return timerValue
	 */
	public int getTimerValue() {
		return timerValue*100;
	}
 }

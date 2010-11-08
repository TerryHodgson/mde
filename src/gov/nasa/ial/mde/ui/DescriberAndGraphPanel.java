package gov.nasa.ial.mde.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;
import gov.nasa.ial.mde.ui.graph.CartesianGraph;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DescriberAndGraphPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6145441370339707300L;
	private  Solver solver;
	private Describer describer;
	private JLabel instructions;
	private JTextField input;
	private JTextArea output;
	private MdeSettings currentSettings;
	private JPanel describerPanel;
	private JButton submitButton;
	private JCheckBox accessableBox;
	private CartesianGraph graphPanel;
	
	
	public DescriberAndGraphPanel() {
		setupMDE();
		this.setLayout(new BorderLayout());
		this.describerPanel = new JPanel();
		this.graphPanel = new CartesianGraph(solver, currentSettings);
		
		setupDescriberPanel();
		setupGraphPanel();
		
		
		
		describerPanel.setPreferredSize(new Dimension(480,480));
		graphPanel.setPreferredSize(new Dimension(480,480));
		this.add(describerPanel, BorderLayout.WEST);
		this.add(graphPanel, BorderLayout.EAST);
	}

	
	
	private void setupMDE() {
		this.currentSettings = new MdeSettings("myAppsMdeProperties");
		solver = new Solver();
		this.describer = new Describer(solver, currentSettings);
		
		//we can change this if we wanted to change what
		this.describer.setOutputFormat(Describer.TEXT_OUTPUT);  
	}

	private void setupDescriberPanel() {
		instructions = new JLabel("Enter equation and press enter:");

		//setup output
		output = new JTextArea("Your description will appear here.");
		output.setEditable(false);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		output.setPreferredSize(new Dimension (480, 360));
		
		//input
		input = new JTextField(20);
		input.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int val = e.getKeyCode();
				if(val == KeyEvent.VK_ENTER){
					output.setText(processEquation(input.getText()));
					input.setText("");
				}
			}
		});	
		
		
		//submit
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				output.setText(processEquation(input.getText()));
				input.setText("");
			}
		});
		
		
		//checkbox
		accessableBox = new JCheckBox("Accessable TTS", MdeSettings.ACCESSIBLE_TTS_DEFAULT);
		accessableBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (accessableBox.isSelected()){
					MdeSettings.ACCESSIBLE_TTS = true;
				}
				else if(!accessableBox.isSelected()){
					MdeSettings.ACCESSIBLE_TTS = false;
				}
			}		
		});
		
		//put it all together
		JPanel inputPanel = new JPanel();
		inputPanel.add(instructions);
		inputPanel.add(input);
		
		describerPanel.add(inputPanel, BorderLayout.NORTH);
		describerPanel.add(accessableBox, BorderLayout.NORTH);
		describerPanel.add(submitButton, BorderLayout.NORTH);
		describerPanel.add(output, BorderLayout.CENTER);
	}
	
	private void setupGraphPanel() {
	}

	private String processEquation(String equation) {
		String description ="OH DEAR GOD\n FULL TILT\nPLEASE RESTART\n";
		try{
			solver.removeAll();
			solver.add(equation);
			solver.solve();
			
			if(solver.anyGraphable())
			{
				graphPanel.drawGraph();
			}
			
			if (solver.anyDescribable()) {
				description = describer.getDescriptions("standards");
					
			} else {
				description="MDE could not generate a description for " + equation + ".";
			}
			
			//
			
		}catch (Exception e) 
		{
			System.out.println(e);
			solver.removeAll();
		}
		return description;
	}
	
	private void toggleAccessable(){
	}
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Math Description Engine");
		DescriberAndGraphPanel panel = new DescriberAndGraphPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        //frame.toFront();
	}
	

}
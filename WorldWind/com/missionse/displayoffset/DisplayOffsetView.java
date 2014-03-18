package com.missionse.displayoffset;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DisplayOffsetView implements java.util.Observer
{
	private JFrame frame;
	private JPanel panel;
	
	private JButton rangeInButton;
	private JButton rangeOutButton;
	private JLabel currentRangeLabel;
	private JTextField currentRangeTextField;

	public DisplayOffsetView()
	{
		this.frame = new JFrame("Display Offset");
		this.frame.setVisible(true);
		this.frame.setTitle("Display Offset");
		this.frame.setSize(500, 400);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new JPanel();;
		this.rangeInButton = new JButton("Range In");
		this.rangeOutButton = new JButton("Range Out");
		this.currentRangeLabel = new JLabel("Current Range");
		this.currentRangeTextField = new JTextField("            ");
		
		this.panel.add(this.currentRangeLabel);
		this.panel.add(this.currentRangeTextField);
		this.panel.add(this.rangeInButton);
		this.panel.add(this.rangeOutButton);
		this.frame.add("North", this.panel);
		//this.frame.pack();
		this.frame.setVisible(true);
	}
	
	public void update(Observable obs, Object obj)
	{
		this.currentRangeTextField.setText("" + ((Integer) obj).intValue());
	}
	
	public void updateRangeTextBox(int value)
	{
		this.currentRangeTextField.setText("" + value);
	}
	
	public void addController(ActionListener controller)
	{
			this.rangeInButton.addActionListener(controller);
			this.rangeOutButton.addActionListener(controller);
	}
	
	public static class CloseListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}

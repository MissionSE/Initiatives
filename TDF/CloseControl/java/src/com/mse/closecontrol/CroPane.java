package com.mse.closecontrol;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CroPane extends JTabbedPane implements ChangeListener {
	private CroPanel priHook; 
	private CroPanel secHook; 
	
	
	public CroPane() {
		priHook = new CroPanel("Primary Hook");
		secHook = new CroPanel("Secondary Hook");
		setupPane();
		this.addChangeListener(this);
	}

	private void setupPane() {
		
		priHook.add(new Label(""));
		secHook.add(new Label(""));
		addTab("Primary Hook", priHook);
		addTab("Secondary Hook", secHook);
		
	}

	@Override
	public void stateChanged(ChangeEvent change) {
		System.err.println("Tab changed: " + this.getSelectedIndex() );

		if (this.getSelectedIndex() == 0) {
			priHook.repaint();

		}
		if (this.getSelectedIndex() == 1) {
			secHook.repaint();
		}
		revalidate();
		repaint();
	}
	
}

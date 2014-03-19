package com.mse.closecontrol;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.Timer;

public class CroPane extends JTabbedPane implements ActionListener {
	private static final long serialVersionUID = 1L;
	private CroPanel priHook; 
	private CroPanel secHook; 

	private Timer dataBaseTimer;
	private SqlReader trackDB;
	
	
	public CroPane() {
		priHook = new CroPanel("Primary Hook");
		secHook = new CroPanel("Secondary Hook");
		setupPane();
		trackDB = new SqlReader();
		dataBaseTimer = new Timer(250, this);
		dataBaseTimer.start();
	}

	private void setupPane() {
		
		priHook.add(new Label(""));
		secHook.add(new Label(""));
		addTab("Primary Hook", priHook);
		addTab("Secondary Hook", secHook);
		
	}
	public void actionPerformed(ActionEvent arg0) {
		TrackData priTrack = new TrackData();
		TrackData secTrack = new TrackData();
		trackDB.executeTrackRead(priTrack, secTrack);
		priHook.updateHookLabels(priTrack);
		secHook.updateHookLabels(secTrack);
		revalidate();
		repaint();
	}

	
}

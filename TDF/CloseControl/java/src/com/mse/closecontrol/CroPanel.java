package com.mse.closecontrol;


import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.Timer;

public class CroPanel extends JPanel implements ActionListener {

	private JLabel tnLabel;
	private JLabel tnValue;

	private JLabel identityLbl;
	private JLabel identityValue;

	private JLabel categoryLbl;
	private JLabel categoryValue;

	private JLabel platformLbl;
	private JLabel platformValue;

	private JLabel latitudeLbl;
	private JLabel latitudeValue;

	private JLabel longitudeLbl;
	private JLabel longitudeValue;

	private JLabel speedLabel;
	private JLabel speedValue;

	private JLabel courseLabel;
	private JLabel courseValue;

	private String panelName =null; 
	
	private Timer dataBaseTimer;
	private SqlReader trackDB;
	public CroPanel(String pName) {
		setBackground(UIManager.getColor("Button.darkShadow"));
		setPanelName(pName);
		setupPanel();
		trackDB = new SqlReader();
		dataBaseTimer = new Timer(1000, this);
		dataBaseTimer.start();
	}
	
	private void setupPanel() {
		GridLayout idLabels = new GridLayout(9, 2);
		setLayout(idLabels);
		//Setup Static labels
		tnLabel = new JLabel("Track Number: ");
		identityLbl = new JLabel("Identity: ");
		categoryLbl = new JLabel("Category: ");
		platformLbl = new JLabel("Platform: ");
		latitudeLbl = new JLabel("Latitude: ");
		longitudeLbl= new JLabel("Longitude: ");
		speedLabel  = new JLabel("Speed: ");
		courseLabel = new JLabel("Course: ");
		
		setLabelFont(tnLabel, Color.YELLOW);
		setLabelFont(identityLbl, Color.YELLOW);
		setLabelFont(categoryLbl, Color.YELLOW);
		setLabelFont(platformLbl, Color.YELLOW);
		setLabelFont(latitudeLbl, Color.YELLOW);
		setLabelFont(longitudeLbl, Color.YELLOW);
		setLabelFont(speedLabel, Color.YELLOW);
		setLabelFont(courseLabel, Color.YELLOW);

		
		//Setup Value Labels
		tnValue = new JLabel("aaa");
		identityValue = new JLabel(" ");
		categoryValue = new JLabel(" ");
		platformValue =  new JLabel(" ");
		latitudeValue =  new JLabel(" ");
		longitudeValue= new JLabel(" ");
		speedValue = new JLabel(" ");
		courseValue = new JLabel(" ");

		setLabelFont(tnValue, Color.WHITE);
		setLabelFont(identityValue, Color.WHITE);
		setLabelFont(categoryValue, Color.WHITE);
		setLabelFont(platformValue, Color.WHITE);
		setLabelFont(latitudeValue, Color.WHITE);
		setLabelFont(longitudeValue, Color.WHITE);
		setLabelFont(speedValue, Color.WHITE);
		setLabelFont(courseValue,Color.WHITE);
		


		add(tnLabel);
		add(tnValue);

		add(identityLbl);
		add(identityValue);
		
		add(categoryLbl);
		add(categoryValue);

		add(platformLbl);
		add(platformValue);

		add(latitudeLbl);
		add(latitudeValue);

		add(longitudeLbl);
		add(longitudeValue);

		add(speedLabel);
		add(speedValue);

		add(courseLabel);
		add(courseValue);


	}
	public void setLabelFont(JLabel lbl, Color color) {

		lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		lbl.setForeground(color);
	}
	public String getPanelName() {
		return panelName;
	}
	private void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	void updateHookLabels(TrackData priHook, TrackData secHook) {
		System.out.println("PH: " + priHook.getTrackNumber());
		tnValue.setText(priHook.getTrackNumber());
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.err.println("Action performed");
		TrackData priHook=new TrackData();
		TrackData secHook=new TrackData();
		trackDB.executeTrackRead(priHook, secHook);
		if (priHook != null) {
			updateHookLabels(priHook, secHook);
		}
	}	

}

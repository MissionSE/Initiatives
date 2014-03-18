package com.missionse.trackplugin;

public interface Track
{
	public int getTrackID();

	public double getLatitude();

	public double getLongitude();

	public double getAltitude();

	public String getAlignment();

	public String getReportingSource();

	public void printData();

	public void updatePosition();

	public void display();
	
	public void toggleHistory();

	public void update();
	
	public void hook();

	public void unhook();

	public boolean isHistoryDisplayed();
};

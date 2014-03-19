package com.missionse.graphicplugin;

public interface Shape {
	
	public String getShapeType();

	public Boolean draw();
	
	public double getLatitude();
	
	public double getLongitude();
	
	public void printData();
	
	public void display();

	public void update();
	
	public void hook();

	public void unhook();

	public int getGraphicID();
}

package com.missionse.graphicplugin;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Ellipsoid;

public class WorldwindEllipsoid implements Shape {

	
	
    Ellipsoid ellipsoid = new Ellipsoid(Position.fromDegrees(40, -120, 80000), 50000, 50000, 50000);
    
    
	public String getShapeType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean draw() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getLatitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLongitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void printData() {
		// TODO Auto-generated method stub
		
	}

	public void display() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void hook() {
		// TODO Auto-generated method stub
		
	}

	public void unhook() {
		// TODO Auto-generated method stub
		
	}

	public int getGraphicID() {
		// TODO Auto-generated method stub
		return 0;
	}
   
}

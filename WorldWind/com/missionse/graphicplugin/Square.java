package com.missionse.graphicplugin;

import gov.nasa.worldwind.geom.Angle;

public abstract class Square implements Shape{

	protected LatLongPoint centerPoint;
	protected double size;
	protected Angle heading;
	
	public Square()
	{	
	}
	
	public Square(LatLongPoint center, double size)
	{
      this.centerPoint = center;
	  this.size = size;  
	}
	
	public void setCenterPoint(LatLongPoint latLongPoint) {
		this.centerPoint = latLongPoint;
	}
	
	public void setAngle(Angle heading)
	{
		this.heading = heading;
	}
	
	public String getShapeType() {
		return "Square";
	}

	public Boolean draw() {
		// TODO Auto-generated method stub
		return true;
	}

}

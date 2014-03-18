package com.missionse.graphicplugin;

public class OverlayEntryModel extends java.util.Observable
{
	//Model should actually contain a Shape object.
  private double latitude;
  private double longitude;
  private double radius;
  
	public double getLatitude()
	{
		return latitude;
	}
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public void sendModelUpdate()
	{
		setChanged();
		notifyObservers();
	}
}

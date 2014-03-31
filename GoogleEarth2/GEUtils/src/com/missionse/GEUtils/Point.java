package com.missionse.GEUtils;


public class Point {
	
	public Point (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point (double x, double y) {
		this(x,y,0);
	}
	
	// lat, lng, altitude or anything you want.
	public double x;
	public double y;
	public double z;
}

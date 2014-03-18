package com.missionse.graphicplugin;

public abstract class Circle implements Shape {

	protected double radius;
	protected LatLongPoint centerPoint;
	protected int graphicID;
	

	public Circle() {
		setRadius(0);
	}

	public Circle(double radius, double latitude, double longitude) {
		this.radius = radius;
		this.centerPoint = new LatLongPoint();
		this.centerPoint.setLatLong(latitude, longitude);
	}

	public Circle(double radius, LatLongPoint centerPoint) {
		this.radius = radius;
		this.centerPoint = centerPoint;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		if (radius > 0) {
			this.radius = radius;
		}
	}

	public double getCircumference() {
		return (2 * Math.PI * Math.pow(this.radius, 2));
	}

	public LatLongPoint getCenterPoint() {
		return this.centerPoint;
	}

	public void setCenterPoint(LatLongPoint centerPoint) {
		this.centerPoint = centerPoint;
	}

	public void setCenterPoint(double latitude, double longitude) {
		this.centerPoint.setLatLong(latitude, longitude);
	}

	public String getShapeType() {
		return "Circle";
	}

}

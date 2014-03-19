package com.missionse.graphicplugin;

import java.awt.Color;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.util.WWUtil;

public final class WorldwindCircle extends Circle{
	
	private ShapeAttributes attrs;
	private SurfaceCircle shape;
	protected boolean enabled = true;
	protected boolean enableEdit = true;
	protected boolean resizeNewShapes;
  protected SurfaceCircleEditor editor;
	
	public WorldwindCircle()
	{
		//setRadius(1000000000);
		//LatLongPoint point = new LatLongPoint(40.0, -75.0);
		//setCenterPoint(point);
	}
	
	public WorldwindCircle(double radius, double latitude, double longitude, AppFrame parent)
	{
		super(radius, latitude, longitude);
		draw();
		this.editor = new SurfaceCircleEditor(parent.getWwd(), shape);
		this.editor.setArmed(true);
		this.editor.enable();
	}

	public Boolean draw() {	
            attrs = new BasicShapeAttributes();
            attrs.setInteriorMaterial(Material.BLUE);
            attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.WHITE)));
            attrs.setInteriorOpacity(0.5);
            attrs.setOutlineOpacity(0.8);
            attrs.setOutlineWidth(3);
			
			shape = new SurfaceCircle(LatLon.fromDegrees(
					this.centerPoint.getLatitude(),
					this.centerPoint.getLongitude()), this.radius);
			shape.setAttributes(attrs);
			
			System.out.println("WorldwindCircle::draw");
			return true;
	
	};

	public void setShape(SurfaceCircle shape)
	{
		this.shape = shape;		
	}
	
	public SurfaceCircle getShape(){return this.shape;}

	public double getLatitude() {
		return centerPoint.getLatitude();
	
	}

	public double getLongitude() {
		return centerPoint.getLongitude();
		
	}

	public void printData() {
		System.out.printf("CIRCLE DATA Lat = %d Long = %d", 
				centerPoint.getLatitude(), centerPoint.getLongitude());
	}

	public void display() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		
		//System.out.printf("UPDATE CIRCLE\n");
		// TODO Auto-generated method stub
		
	}

	public void hook() {
		System.out.printf("WorldwindCircle Hook");
		
	}

	public void unhook() {
		// TODO Auto-generated method stub
		
	}

	public int getGraphicID() {
		return graphicID;
	}

		
};




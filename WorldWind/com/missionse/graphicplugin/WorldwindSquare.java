package com.missionse.graphicplugin;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.SurfaceSquare;
import gov.nasa.worldwind.render.SurfaceShape;
import gov.nasa.worldwind.util.WWUtil;

import java.awt.Color;

public final class WorldwindSquare extends Square{	
	
	private RenderableLayer layer;
	private ShapeAttributes attributes;
	private SurfaceShape shape;
	
	public WorldwindSquare()
	{
		LatLongPoint latLongPoint = new LatLongPoint(30, -75);
		setCenterPoint(latLongPoint);
		this.size = 1000000;
		//Angle ang = new Angle(90.0, 90.0);
		//setAngle(ang);
	}
	@Override
	public Boolean draw() {
			shape = new SurfaceSquare(LatLon.fromDegrees(this.centerPoint.getLatitude(), 
					this.centerPoint.getLongitude()), this.size, this.heading);
			setAttributes(shape);

			setShape(shape);
			
			return true;
	};

	public void setShape(SurfaceShape shape)
	{
		this.shape = shape;		
	}
	
	public SurfaceShape getShape(){return this.shape;}
	
	public void setAttributes(SurfaceShape shape) {
		attributes = new BasicShapeAttributes();
		attributes.setInteriorMaterial(Material.GREEN);
		attributes.setOutlineMaterial(new Material(WWUtil
				.makeColorBrighter(Color.GREEN)));
		attributes.setInteriorOpacity(0.5);
		attributes.setOutlineOpacity(0.8);
		attributes.setOutlineWidth(3);
		shape.setAttributes(attributes);
	};

	public void addToLayer(SurfaceShape shape) {
		layer.addRenderable(shape);
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
	};
}

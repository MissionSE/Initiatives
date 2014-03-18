package com.missionse.graphicplugin;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.SurfaceText;

public final class WorldwindText extends Text{

	public WorldwindText()
	{
		draw();
	}

	//private RenderableLayer layer;
	//private ShapeAttributes attributes;
	private SurfaceText shape;
	
	@Override
	public Boolean draw() {
			shape = new SurfaceText("Hello", Position.fromDegrees(35, -75));
			return true;
	};
	
	/*public void setAttributes(SurfaceText shape2) {
		attributes = new BasicShapeAttributes();
		attributes.setInteriorMaterial(Material.GREEN);
		attributes.setOutlineMaterial(new Material(WWUtil
				.makeColorBrighter(Color.GREEN)));
		attributes.setInteriorOpacity(0.5);
		attributes.setOutlineOpacity(0.8);
		attributes.setOutlineWidth(3);
		shape2.setAttributes(attributes);*/
//	};

	public void setShape(SurfaceText shape)
	{
		this.shape = shape;		
	}
	
	public SurfaceText getShape(){return this.shape;}
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

package com.missionse.graphicplugin;

import gov.nasa.worldwind.geom.Position;

public abstract class Text implements Shape {

	protected Position position;
	protected CharSequence text_value;
	
	public Text()
	{
		
	}
	
	public Text(String text, Position position)
	{
		this.text_value = text;
		this.position = position;
	}
	
	public void setText(CharSequence text)
	{
		this.text_value = text;
	}

	public String getShapeType() {
		// TODO Auto-generated method stub
		return "Text";
	}

	public Boolean draw() {
		// TODO Auto-generated method stub
		return true;
	}

}

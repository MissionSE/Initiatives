package com.missionse.graphicplugin;

import java.util.HashMap;
import java.util.Vector;

public class GraphicRepository
{
	Vector<Shape> graphicVector;
	HashMap<Integer, Shape> graphicFinder;

	public enum ShapeType {CIRCLE, SQUARE};
	
	public GraphicRepository()
	{
		this.graphicVector = new Vector<Shape>();
		this.graphicFinder = new HashMap<Integer, Shape>();
	}

	public Vector<Shape> getGraphicData()
	{
		return this.graphicVector;
	}
	
	public HashMap<Integer, Shape> getGraphicFinder()
	{
		return this.graphicFinder;
	}
	
	public Shape getShapeFromHashCode(int hashCode)
	{
		return graphicFinder.get(hashCode);
	}

	public void addShape(Shape graphic)
	{
		System.out.printf("GraphicDataSource::addShape");
		this.graphicVector.add(graphic);
	}
	
	public void deleteShape(int hashCode)
	{
		
	}
	
}

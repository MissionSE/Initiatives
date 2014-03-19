package com.missionse.graphicplugin;

import java.util.Vector;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class GraphicPresentation implements Presentation
{
	AppFrame frame;
	
	GraphicRepository graphicRepository;

	public GraphicPresentation(GraphicRepository dataSource)
	{
		this.graphicRepository = dataSource;
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
	}

	public Presentation getPresentation()
	{
		return this;
	}
	
	public Vector<Shape> getGraphicData()
	{
		return graphicRepository.getGraphicData();
	}

}
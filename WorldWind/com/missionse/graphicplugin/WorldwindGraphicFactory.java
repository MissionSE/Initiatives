package com.missionse.graphicplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindGraphicFactory implements GraphicFactory
{
	static AppFrame parent;
	static WorldwindGraphicPresentation graphicPresentation;
	static GraphicRepository graphicRepository;
	static WorldwindGraphicController graphicController;
	static WorldwindGraphicHookManager graphicHookManager;

	public WorldwindGraphicFactory(AppFrame parent)
	{
		WorldwindGraphicFactory.parent = parent;
	}
	
	public AppFrame getAppFrame()
	{
	  return parent;
	}

	public GraphicPresentation getGraphicPresentation()
	{
		if (WorldwindGraphicFactory.graphicPresentation == null)
		{
			WorldwindGraphicFactory.graphicPresentation = new WorldwindGraphicPresentation(getGraphicRepository());
			WorldwindGraphicFactory.graphicPresentation.initialize(parent);
		}
		return WorldwindGraphicFactory.graphicPresentation;
	}

	public GraphicController getGraphicController()
	{
		if (WorldwindGraphicFactory.graphicController == null)
		{
			WorldwindGraphicFactory.graphicController = new WorldwindGraphicController(getGraphicRepository(), getGraphicPresentation());
		}
		return WorldwindGraphicFactory.graphicController;
	}

	public GraphicRepository getGraphicRepository()
	{
		if (WorldwindGraphicFactory.graphicRepository == null)
		{
			WorldwindGraphicFactory.graphicRepository = new GraphicRepository();
		}
		return WorldwindGraphicFactory.graphicRepository;
	}
	
	public GraphicHookManager getGraphicHookManager()
	{
		if(WorldwindGraphicFactory.graphicHookManager == null)
		{
			WorldwindGraphicFactory.graphicHookManager = new WorldwindGraphicHookManager(getGraphicRepository());
			WorldwindGraphicFactory.graphicHookManager.initialize(parent);
		}
		return graphicHookManager;
	}
	
	public Circle createCircle(double radius, double latitude, double longitude)
	{
		Circle circle = new WorldwindCircle(radius, latitude, longitude, parent);
		getGraphicRepository().addShape(circle);
		return circle;
	}

}

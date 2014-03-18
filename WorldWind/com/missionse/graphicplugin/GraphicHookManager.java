package com.missionse.graphicplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class GraphicHookManager
{
	Shape currentHook;
	GraphicRepository graphicRepository;
	AppFrame frame;

	public GraphicHookManager(GraphicRepository graphicDataSource)
	{
		this.graphicRepository = graphicDataSource;
	}
	
	public void initialize(AppFrame parent)
	{
		this.frame = parent;
	}

	public void unhook()
	{
		this.currentHook.unhook();
		this.currentHook = null;
	}

	public void hook(int hashCode)
	{
		if (this.currentHook != null)
		{
		  this.currentHook.unhook();
		}
		
		this.currentHook = graphicRepository.getShapeFromHashCode(hashCode);
		
		if (this.currentHook != null)
		{
			this.currentHook.hook();
		}
	}
}
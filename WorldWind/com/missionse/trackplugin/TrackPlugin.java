package com.missionse.trackplugin;

import java.util.ArrayList;

import com.missionse.worldwind.ApplicationTemplate;
import com.missionse.worldwind.Plugin;

public class TrackPlugin implements Plugin
{
	ApplicationTemplate.AppFrame parent;
	TrackFactory trackFactory;
	TrackHookManager trackHookManager;
	ArrayList<TrackController> trackControllers;

	public TrackPlugin()
	{
		this.trackControllers = new ArrayList<TrackController>();
	}

	public String getPluginName()
	{
		return "TrackPlugin";
	}

	public void update()
	{
		for (TrackController tc : trackControllers)
		{
			tc.update();
		}
		
		this.trackHookManager.update();
		this.parent.getWwd().redraw();
	}
	
	public void hookUpdate(int hashCode)
	{
		if (hashCode != -1)
		{
	    this.trackHookManager.hook(hashCode);
		}
		else
		{
			this.trackHookManager.unhook();
		}
	}

	public void initialize(com.missionse.worldwind.ApplicationTemplate.AppFrame parent)
	{
		this.parent = parent;
		this.trackFactory = new WorldwindTrackFactory(parent);
		this.trackControllers.add(this.trackFactory.getTrackController());
		this.trackControllers.add(this.trackFactory.getTrackHistoryController());
		this.trackHookManager = this.trackFactory.getTrackHookManager();
	}

}

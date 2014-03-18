package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate;
import com.missionse.worldwind.Plugin;

public class TrackPlugin implements Plugin
{
	ApplicationTemplate.AppFrame parent;
	TrackController trackController;
	TrackFactory trackFactory;
	TrackHookManager trackHookManager;
	
	WorldwindTrackHistoryController thController;
	WorldwindTrackHistoryPresentation thPresentation;
	

	public TrackPlugin()
	{
	}

	public String getPluginName()
	{
		return "TrackPlugin";
	}

	public void update()
	{
		this.trackController.update();
		this.thController.update();
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
		this.trackController = this.trackFactory.getTrackController();
		this.trackHookManager = this.trackFactory.getTrackHookManager();
		
		this.thPresentation = new WorldwindTrackHistoryPresentation();
		this.thPresentation.initialize(this.parent);
		this.thController = new WorldwindTrackHistoryController(this.trackFactory.getTrackDataSource(), this.thPresentation, this.parent );
	}

}

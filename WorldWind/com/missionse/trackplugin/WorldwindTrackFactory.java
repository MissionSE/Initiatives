package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackFactory implements TrackFactory
{
	static AppFrame parent;
	static WorldwindTrackPresentation trackPresentation;
	static TrackDataSource trackDataSource;
	static WorldwindTrackController trackController;
	static WorldwindTrackHookManager trackHookManager;

	public WorldwindTrackFactory(AppFrame parent)
	{
		WorldwindTrackFactory.parent = parent;
	}
	
	public AppFrame getAppFrame()
	{
	  return parent;
	}

	public TrackPresentation getTrackPresentation()
	{
		if (WorldwindTrackFactory.trackPresentation == null)
		{
			WorldwindTrackFactory.trackPresentation = new WorldwindTrackPresentation(/*getTrackDataSource()*/);
			WorldwindTrackFactory.trackPresentation.initialize(parent);
		}
		return WorldwindTrackFactory.trackPresentation;
	}

	public TrackController getTrackController()
	{
		if (WorldwindTrackFactory.trackController == null)
		{
			WorldwindTrackFactory.trackController = new WorldwindTrackController(getTrackDataSource(), getTrackPresentation());
		}
		return WorldwindTrackFactory.trackController;
	}

	public Track getRandomTrack()
	{
		return new WorldwindTrack();
	}

	public TrackDataSource getTrackDataSource()
	{
		if (WorldwindTrackFactory.trackDataSource == null)
		{
			WorldwindTrackFactory.trackDataSource = new TrackDataSource();
		}
		return WorldwindTrackFactory.trackDataSource;
	}
	
	public TrackHookManager getTrackHookManager()
	{
		if(WorldwindTrackFactory.trackHookManager == null)
		{
			WorldwindTrackFactory.trackHookManager = new WorldwindTrackHookManager(getTrackDataSource());
			WorldwindTrackFactory.trackHookManager.initialize(parent);
		}
		return trackHookManager;
	}

	public Track getTrackHistoryController()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

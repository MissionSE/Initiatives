package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackFactory implements TrackFactory
{
	static AppFrame parent;
	static TrackDataSource trackDataSource;
	static WorldwindTrackController trackController;
	static WorldwindTrackHistoryController trackHistoryController;
	static WorldwindTrackHistoryPresentation trackHistoryPresentation;
	static WorldwindTrackPresentation trackPresentation;
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
			WorldwindTrackFactory.trackPresentation = new WorldwindTrackPresentation();
			WorldwindTrackFactory.trackPresentation.initialize(parent);
		}
		return WorldwindTrackFactory.trackPresentation;
	}

	public TrackPresentation getTrackHistoryPresentation()
	{
		if (WorldwindTrackFactory.trackHistoryPresentation == null)
		{
			WorldwindTrackFactory.trackHistoryPresentation = new WorldwindTrackHistoryPresentation();
			WorldwindTrackFactory.trackHistoryPresentation.initialize(parent);
		}
		return WorldwindTrackFactory.trackHistoryPresentation;
	}

	public TrackController getTrackController()
	{
		if (WorldwindTrackFactory.trackController == null)
		{
			WorldwindTrackFactory.trackController = new WorldwindTrackController(getTrackDataSource(), getTrackPresentation());
		}
		return WorldwindTrackFactory.trackController;
	}

	public TrackController getTrackHistoryController()
	{
		if (WorldwindTrackFactory.trackHistoryController == null)
		{
			WorldwindTrackFactory.trackHistoryController = new WorldwindTrackHistoryController(getTrackDataSource(),
					getTrackHistoryPresentation(), parent);
		}
		return WorldwindTrackFactory.trackHistoryController;
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
		if (WorldwindTrackFactory.trackHookManager == null)
		{
			WorldwindTrackFactory.trackHookManager = new WorldwindTrackHookManager(getTrackDataSource());
			WorldwindTrackFactory.trackHookManager.initialize(parent);
		}
		return trackHookManager;
	}
}

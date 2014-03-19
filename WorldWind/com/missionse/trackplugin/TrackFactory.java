package com.missionse.trackplugin;

public interface TrackFactory
{
	public TrackPresentation getTrackPresentation();

	public TrackController getTrackController();

	public Track getRandomTrack();

	public TrackDataSource getTrackDataSource();
	
	public TrackHookManager getTrackHookManager();

	public TrackController getTrackHistoryController();
}

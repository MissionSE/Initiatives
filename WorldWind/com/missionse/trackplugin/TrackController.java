package com.missionse.trackplugin;

public class TrackController
{
	TrackDataSource trackDataSource;
	TrackPresentation trackPresentation;

	public TrackController(TrackDataSource dataSource, TrackPresentation trackPresentation)
	{
		this.trackDataSource = dataSource;
		this.trackPresentation = trackPresentation;
	}

	public TrackDataSource getTrackDataSource()
	{
		return trackDataSource;
	}

	public TrackPresentation getTrackPresentation()
	{
		return trackPresentation;
	}

	public void update()
	{
	}
}

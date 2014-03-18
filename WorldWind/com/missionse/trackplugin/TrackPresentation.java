package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class TrackPresentation implements Presentation
{
	AppFrame frame;
	//TrackDataSource trackDataSource;

	public TrackPresentation(/*TrackDataSource dataSource*/)
	{
		//this.trackDataSource = dataSource;
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
	}

	public Presentation getPresentation()
	{
		return this;
	}

	//public Vector<Track> getTrackData()
	//{
	//	return trackDataSource.getTrackData();
	//}
}

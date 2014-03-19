package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class TrackPresentation implements Presentation
{
	AppFrame frame;

	public TrackPresentation()
	{
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
	}

	public Presentation getPresentation()
	{
		return this;
	}
}

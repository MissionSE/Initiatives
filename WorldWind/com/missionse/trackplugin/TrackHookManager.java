package com.missionse.trackplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class TrackHookManager
{
	Track currentHook;
	Track offsetTrack;
	TrackDataSource trackDataSource;
	AppFrame frame;

	public TrackHookManager(TrackDataSource trackDataSource)
	{
		this.trackDataSource = trackDataSource;
	}
	
	public void initialize(AppFrame parent)
	{
		this.frame = parent;
	}

	public void unhook()
	{
		if (this.currentHook != null)
		{
			this.currentHook.unhook();
		}
		this.currentHook = null;
	}

	public void hook(int hashCode)
	{
		if (this.currentHook != null)
		{
		  this.currentHook.unhook();
		}
		this.currentHook = trackDataSource.getTrackFromHashCode(hashCode);
		if (this.currentHook != null)
		{
			this.currentHook.hook();
		}
	}

	public void update()
	{
	}
}

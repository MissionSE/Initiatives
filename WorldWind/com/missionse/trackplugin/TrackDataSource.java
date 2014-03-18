package com.missionse.trackplugin;

import java.util.HashMap;
import java.util.Vector;

public class TrackDataSource
{
	static int MAX_TRACKS = 5000;
	Vector<Track> trackVector;
	HashMap<Integer, Track> trackFinder;

	public TrackDataSource()
	{
		this.trackVector = new Vector<Track>();
		this.trackFinder = new HashMap<Integer, Track>();
		for (int i = 0; i < MAX_TRACKS; i++)
		{
			this.trackVector.add(new WorldwindTrack());
		}
	}

	public Vector<Track> getTrackData()
	{
		return this.trackVector;
	}
	
	public HashMap<Integer, Track> getTrackFinder()
	{
		return this.trackFinder;
	}
	
	public Track getTrackFromHashCode(int hashCode)
	{
		return trackFinder.get(hashCode);
	}

	public Track getTrackData(int trackID)
	{
		for (int i = 0; i < MAX_TRACKS; i++)
		{
			if (trackID == trackVector.get(i).getTrackID())
			{
				return trackVector.get(i);
			}
		}
		return new WorldwindTrack();
	}
}

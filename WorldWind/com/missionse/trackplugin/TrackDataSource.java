package com.missionse.trackplugin;

import java.util.ArrayList;
import java.util.HashMap;

public class TrackDataSource
{
	static int MAX_TRACKS = 5000;
	ArrayList<Track> tracks;
	HashMap<Integer, Track> trackFinder;

	public TrackDataSource()
	{
		this.tracks = new ArrayList<Track>();
		this.trackFinder = new HashMap<Integer, Track>();
		for (int i = 0; i < MAX_TRACKS; i++)
		{
			this.tracks.add(new WorldwindTrack());
		}
	}

	public ArrayList<Track> getTrackData()
	{
		return this.tracks;
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
			if (trackID == tracks.get(i).getTrackID())
			{
				return tracks.get(i);
			}
		}
		return new WorldwindTrack();
	}
}

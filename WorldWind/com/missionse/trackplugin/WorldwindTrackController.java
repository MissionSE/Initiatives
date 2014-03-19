package com.missionse.trackplugin;

import gov.nasa.worldwind.render.WWIcon;
import gov.nasa.worldwind.render.markers.Marker;

import java.util.ArrayList;

public class WorldwindTrackController extends TrackController
{
	ArrayList<Marker> trackMarkers;
	ArrayList<WWIcon> trackIcons;

	public WorldwindTrackController(TrackDataSource dataSource, TrackPresentation trackPresentation)
	{
		super(dataSource, trackPresentation);

		trackMarkers = new ArrayList<Marker>();
		trackIcons = new ArrayList<WWIcon>();
		ArrayList<Track> tracks = trackDataSource.getTrackData();

		for (Track t : tracks)
		{
			if (t instanceof WorldwindTrack)
			{
				WorldwindTrack wwTrack = (WorldwindTrack) t;
				trackDataSource.getTrackFinder().put(wwTrack.getTrackMarker().hashCode(), wwTrack);
				trackMarkers.add(wwTrack.getTrackMarker());
				trackIcons.add(wwTrack.getTrackIcon());
			}
		}
    if (this.trackPresentation instanceof WorldwindTrackPresentation)
    {
    	WorldwindTrackPresentation wwPresentation = (WorldwindTrackPresentation)(this.trackPresentation);
			wwPresentation.getTrackLayer().setMarkers(trackMarkers);
		  wwPresentation.getTrackIconLayer().setIcons(trackIcons);
    }
	}

	public void update()
	{
		ArrayList<Track> tracks = trackDataSource.getTrackData();
		for(Track t : tracks)
		{
			t.update();
		}
	}
}

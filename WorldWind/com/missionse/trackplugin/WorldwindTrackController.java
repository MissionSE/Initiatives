package com.missionse.trackplugin;

import gov.nasa.worldwind.render.UserFacingIcon;
import gov.nasa.worldwind.render.WWIcon;
import gov.nasa.worldwind.render.markers.Marker;

import java.util.Iterator;
import java.util.Vector;

public class WorldwindTrackController extends TrackController
{
	WorldwindTrackPresentation worldwindTrackPresentation;
	Vector<Marker> trackMarkers;
	Vector<WWIcon> trackIcons;

	public WorldwindTrackController(TrackDataSource dataSource, TrackPresentation trackPresentation)
	{
		super(dataSource, trackPresentation);

		if (trackPresentation instanceof WorldwindTrackPresentation)
		{
			this.worldwindTrackPresentation = (WorldwindTrackPresentation) trackPresentation;
		}

		trackMarkers = new Vector<Marker>();
		trackIcons = new Vector<WWIcon>();
		Vector<Track> tracks = trackDataSource.getTrackData();

		Iterator<Track> it = tracks.iterator();
		while (it.hasNext())
		{
			Track t = it.next();
			if (t instanceof WorldwindTrack)
			{
				WorldwindTrack wwTrack = (WorldwindTrack) t;
				trackDataSource.getTrackFinder().put(wwTrack.getTrackMarker().hashCode(), wwTrack);
				trackMarkers.add(wwTrack.getTrackMarker());
				trackIcons.add(wwTrack.getTrackIcon());
			}
		}

		worldwindTrackPresentation.getTrackLayer().setMarkers(trackMarkers);
		worldwindTrackPresentation.getTrackIconLayer().setIcons(trackIcons);
	}

	public void update()
	{
		Vector<Track> tracks = trackDataSource.getTrackData();
		Iterator<Track> it = tracks.iterator();
		while (it.hasNext())
		{
			it.next().update();
		}
	}
}

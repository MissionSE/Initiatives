package com.missionse.trackplugin;

import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.render.markers.Marker;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackHistoryController extends TrackController
{
	WorldwindTrackHistoryPresentation worldwindTrackHistoryPresentation;
	ArrayList<Track> tracksWithHistoryOn;
	CopyOnWriteArrayList<Marker> trackHistoryMarkers;
	AppFrame parent;

	public WorldwindTrackHistoryController(TrackDataSource dataSource, TrackPresentation trackPresentation, AppFrame frame)
	{
		super(dataSource, trackPresentation);
		
		this.parent = frame;
		this.tracksWithHistoryOn = new ArrayList<Track>();
		this.trackHistoryMarkers = new CopyOnWriteArrayList<Marker>();

		if (trackPresentation instanceof WorldwindTrackHistoryPresentation)
		{
			this.worldwindTrackHistoryPresentation = (WorldwindTrackHistoryPresentation) trackPresentation;
		}

		createListener();
	}

	public void update()
	{
		this.trackHistoryMarkers.clear();

		for (Track t : this.tracksWithHistoryOn)
		{
			if (t instanceof WorldwindTrack)
			{
				WorldwindTrack wt = (WorldwindTrack) t;
				wt.displayHistory();
				this.trackHistoryMarkers.addAll(wt.getTrackHistoryMarkers());
			}
		}
		worldwindTrackHistoryPresentation.getTrackLayer().setMarkers(trackHistoryMarkers);
	}

	public void createListener()
	{
		if (this.parent != null)
		{
			this.parent.getWwd().addSelectListener(new SelectListener()
			{
				public void selected(SelectEvent event)
				{
					if (event.getEventAction().equals(SelectEvent.RIGHT_CLICK))
					{
						if (event.getTopObject() != null)
						{
							int hashCode = event.getTopObject().hashCode();
							Track t = getTrackDataSource().getTrackFromHashCode(hashCode);
							t.toggleHistory();
							if (t.isHistoryDisplayed())
							{
								tracksWithHistoryOn.add(t);
							}
							else
							{
								tracksWithHistoryOn.remove(t);
							}
						}
					}
				}
			});
		}
	}

}

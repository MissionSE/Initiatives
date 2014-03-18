package com.missionse.trackplugin;

import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.render.markers.Marker;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackHistoryController extends TrackController
{
	WorldwindTrackHistoryPresentation worldwindTrackHistoryPresentation;
	Vector<Track> tracksWithHistoryOn;
	CopyOnWriteArrayList<Marker> trackHistoryMarkers;
	AppFrame parent;

	public WorldwindTrackHistoryController(TrackDataSource dataSource, TrackPresentation trackPresentation, AppFrame frame)
	{
		super(dataSource, trackPresentation);
		System.out.println("WWTrackHistoryController constructor");
		this.parent = frame;
		this.tracksWithHistoryOn = new Vector<Track>();
		this.trackHistoryMarkers = new CopyOnWriteArrayList<Marker>();
		
		if (trackPresentation instanceof WorldwindTrackHistoryPresentation)
		{
			System.out.println("Initialized WWTrackHistoryPresentation");
			this.worldwindTrackHistoryPresentation = (WorldwindTrackHistoryPresentation) trackPresentation;
		}
		
		createListener();
	}

	public void update()
	{
    Iterator<Track> it = tracksWithHistoryOn.iterator();
    this.trackHistoryMarkers.clear();
    while(it.hasNext())
    {
    	Track t = it.next();
    	if (t instanceof WorldwindTrack)
    	{
    		WorldwindTrack wt = (WorldwindTrack)t;
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
					System.out.println("Received right click event");
					if (event.getTopObject() != null)
					{
						int hashCode = event.getTopObject().hashCode();
						Track t = getTrackDataSource().getTrackFromHashCode(hashCode);
						t.toggleHistory();
						if (t.isHistoryDisplayed())
						{
							System.out.println("Adding track t " + t.getTrackID());
						  tracksWithHistoryOn.add(t);
						}
						else
						{
							System.out.println("Removing track t " + t.getTrackID());
							tracksWithHistoryOn.remove(t);
						}					
					}
				}
			}
		});
		}
	}

}

package com.missionse.trackplugin;

import gov.nasa.worldwind.layers.MarkerLayer;
import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackHistoryPresentation extends TrackPresentation
{
	MarkerLayer trackHistoryLayer;

	public WorldwindTrackHistoryPresentation()
	{
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
		buildPresentationLayer();
	}

	private void buildPresentationLayer()
	{
		this.trackHistoryLayer = new MarkerLayer();
		this.trackHistoryLayer.setOverrideMarkerElevation(true);
		this.trackHistoryLayer.setKeepSeparated(false);
		this.trackHistoryLayer.setElevation(1000d);
		this.trackHistoryLayer.setName("Track History");
		insertLayer(trackHistoryLayer);
	}

	public MarkerLayer getTrackLayer()
	{
		return trackHistoryLayer;
	}

	private void insertLayer(MarkerLayer layer)
	{
		frame.insertBeforePlacenames(frame.getWwd(), trackHistoryLayer);
		frame.getLayerPanel().update(frame.getWwd());
	}
}

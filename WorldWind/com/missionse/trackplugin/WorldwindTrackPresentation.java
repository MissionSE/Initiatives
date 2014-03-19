package com.missionse.trackplugin;

import gov.nasa.worldwind.layers.IconLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.MarkerLayer;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindTrackPresentation extends TrackPresentation
{
	MarkerLayer trackLayer;
	IconLayer trackIconLayer;
	
	public WorldwindTrackPresentation()
	{
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
		buildPresentationLayer();
	}

	private void buildPresentationLayer()
	{
		this.trackLayer = new MarkerLayer();
		this.trackLayer.setOverrideMarkerElevation(true);
		this.trackLayer.setKeepSeparated(false);
		this.trackLayer.setElevation(1000d);
		this.trackLayer.setName("Track Layer");
		insertLayer(trackLayer);
		
		this.trackIconLayer = new IconLayer();
		this.trackIconLayer.setName("Track Icons");
		insertLayer(this.trackIconLayer);
	}

	public MarkerLayer getTrackLayer()
	{
		return trackLayer;
	}
	
	public IconLayer getTrackIconLayer()
	{
		return this.trackIconLayer;
	}

	private void insertLayer(Layer layer)
	{
		frame.insertBeforePlacenames(frame.getWwd(), layer);
		frame.getLayerPanel().update(frame.getWwd());
	}

}

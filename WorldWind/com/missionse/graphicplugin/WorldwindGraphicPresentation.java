package com.missionse.graphicplugin;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AbstractSurfaceShape;

import java.util.Vector;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public class WorldwindGraphicPresentation extends GraphicPresentation
{
	RenderableLayer graphicLayer;
	Vector<AbstractSurfaceShape> graphics;

	public WorldwindGraphicPresentation(GraphicRepository graphicRepository)
	{
		super(graphicRepository);
	}

	public void initialize(AppFrame parent)
	{
		this.frame = parent;
		buildPresentationLayer();
	}

	private void buildPresentationLayer()
	{
		this.graphicLayer = new RenderableLayer();
		this.graphicLayer.setName("Graphic Layer");
		insertLayer(graphicLayer);
	}

	public RenderableLayer getGraphicLayer()
	{
		return graphicLayer;
	}

	private void insertLayer(RenderableLayer layer)
	{
		frame.insertBeforePlacenames(frame.getWwd(), graphicLayer);
		frame.getLayerPanel().update(frame.getWwd());
	}

}


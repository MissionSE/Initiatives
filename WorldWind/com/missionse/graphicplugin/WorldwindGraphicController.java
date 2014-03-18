package com.missionse.graphicplugin;

import java.util.Iterator;
import java.util.Vector;

import gov.nasa.worldwind.render.Renderable;

public class WorldwindGraphicController extends GraphicController
{
	WorldwindGraphicPresentation worldwindGraphicPresentation;

	public WorldwindGraphicController(GraphicRepository dataSource, GraphicPresentation graphicPresentation)
	{
		super(dataSource, graphicPresentation);

		if (graphicPresentation instanceof WorldwindGraphicPresentation)
		{
			this.worldwindGraphicPresentation = (WorldwindGraphicPresentation) graphicPresentation;
		}
	}

	public void updateGraphic()
	{
		Vector<Renderable> graphics = new Vector<Renderable>();
		Vector<Shape> Shapes = graphicRepository.getGraphicData();
		Iterator<Shape> it = Shapes.iterator();
		
		//This design is flaw but leave it for now since the periodic 
		//makes it easier.  Only need to add to presentation once.
		
		while (it.hasNext())
		{
			Shape t = it.next();
			if (t instanceof WorldwindCircle)
			{
				WorldwindCircle wwGraphic = (WorldwindCircle) t;
				graphicRepository.getGraphicFinder().put(wwGraphic.getShape().hashCode(), wwGraphic);
				graphics.add(wwGraphic.getShape());
			}
			worldwindGraphicPresentation.getGraphicLayer().setRenderables(graphics);
		}
	}
	
	public void update()
	{
		Vector<Shape> graphics = graphicRepository.getGraphicData();
		Iterator<Shape> it = graphics.iterator();
		
		while(it.hasNext())
		{
			updateGraphic();
			it.next().update();
		}
	}
}
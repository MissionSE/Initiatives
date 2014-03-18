package com.missionse.graphicplugin;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Balloon;
import gov.nasa.worldwind.render.BalloonAttributes;
import gov.nasa.worldwind.render.BasicBalloonAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.ScreenAnnotationBalloon;
import gov.nasa.worldwind.render.Size;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;

public class WorldwindGraphicHookManager extends GraphicHookManager
{
	RenderableLayer balloonLayer;

	public WorldwindGraphicHookManager(GraphicRepository graphicRepository)
	{
		super(graphicRepository);
	}
	
	public void hook(int hashCode)
	{
		System.out.println("HOOK");
		super.hook(hashCode);
		createAnnotationBalloon();
	}
	
	public void unhook()
	{
		System.out.println("UNHOOK");
		super.unhook();
		removeAnnotationBalloon();
	}
	
  private void createAnnotationBalloon()
  {
  	if (currentHook != null)
  	{
  		if (this.balloonLayer == null)
  		{
  			createBalloonLayer();
  		}
  		
  		System.out.println("createAnnotationBalloon");
  			String s = "Graphic ID: " + currentHook.getGraphicID() + "\n" + "Latitude: "
  					+ (double) Math.round(currentHook.getLatitude() * 100) / 100 + "\n" + "Longitude: "
  					+ (double) Math.round(currentHook.getLongitude() * 100) / 100 + "\n";
  			
  			System.out.printf("Annotation s = %s\n", s);
  					
  			Balloon balloon = new ScreenAnnotationBalloon(s, new Point(600, 600));

  			BalloonAttributes attrs = new BasicBalloonAttributes();
  			attrs.setSize(Size.fromPixels(200, 200));
  			attrs.setOffset(new Offset(0d, 0d, AVKey.PIXELS, AVKey.PIXELS));
  			attrs.setInsets(new Insets(10, 10, 10, 10)); // .
  			attrs.setLeaderShape(AVKey.SHAPE_NONE);
  			attrs.setTextColor(Color.WHITE);
  			attrs.setInteriorMaterial(Material.BLACK);
  			attrs.setInteriorOpacity(0.6);
  			attrs.setOutlineMaterial(Material.WHITE);
  			balloon.setAttributes(attrs);

  			balloonLayer.addRenderable(balloon);
  	}
  }
  
  private void removeAnnotationBalloon()
  {
  	System.out.println("RemoveAnnotationBalloon");
  	if (currentHook != null)
  	{
			balloonLayer.removeAllRenderables();
  	}
  }
  
	public void createBalloonLayer()
	{
		balloonLayer = new RenderableLayer();
		balloonLayer.setName("Graphic Information");
		frame.insertBeforePlacenames(frame.getWwd(), balloonLayer);
		frame.getLayerPanel().update(frame.getWwd());
	}

}
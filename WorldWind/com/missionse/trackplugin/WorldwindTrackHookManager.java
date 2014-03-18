package com.missionse.trackplugin;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
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

public class WorldwindTrackHookManager extends TrackHookManager
{
	RenderableLayer balloonLayer;
	double previousAltitude = 0;

	public WorldwindTrackHookManager(TrackDataSource trackDataSource)
	{
		super(trackDataSource);
	}

	public void hook(int hashCode)
	{
		if (this.offsetTrack != null)
		{
		  if (this.offsetTrack instanceof WorldwindTrack)
		  {
			  WorldwindTrack wwOffset = (WorldwindTrack) this.offsetTrack;
			  {
				  if (wwOffset.getTrackMarker().hashCode() == hashCode)
				  {
					  removeTracker();
					  return;
				  }
		    }
		  }
		}
	
		if (this.currentHook instanceof WorldwindTrack)
		{
			WorldwindTrack wwTrack = (WorldwindTrack) this.currentHook;
			if (wwTrack.getTrackMarker().hashCode() == hashCode)
			{
				System.out.println("WorldwindTrackHookManager::hook");
				createTracker(wwTrack);
			}
		}
		
		unhook();
		super.hook(hashCode);
		createAnnotationBalloon();
	}

	private void removeTracker()
	{
		System.out.println("::removeTracker previousAltitude is " + previousAltitude);
		WorldwindTrack wwTrack = (WorldwindTrack) this.offsetTrack;
		Position p = wwTrack.getTrackMarker().getPosition();
		Position newPosition = new Position(p.getLatitude(), p.getLongitude(), this.previousAltitude);
		this.frame.getWwd().getView().setEyePosition(newPosition);
		this.frame.getWwd().redraw();
		this.offsetTrack = null;
		this.previousAltitude = 0;
	}

	private void createTracker(WorldwindTrack track)
	{
		this.offsetTrack = track;
		this.previousAltitude = this.frame.getWwd().getView().getEyePosition().getAltitude();
	}

	public void unhook()
	{
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
			String s = "Track ID: " + currentHook.getTrackID() + "\n" + "Latitude: "
					+ (double) Math.round(currentHook.getLatitude() * 100) / 100 + "\n" + "Longitude: "
					+ (double) Math.round(currentHook.getLongitude() * 100) / 100 + "\n" + "Altitude: "
					+ currentHook.getAltitude() + "\n" + currentHook.getAlignment() + "   " + currentHook.getReportingSource();
			
			int height = this.frame.getHeight();
			int width = this.frame.getWidth();
			System.out.println("Width: " + width + ", Height: " + height);

			Balloon balloon = new ScreenAnnotationBalloon(s, new Point(900, 850));

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
		if (balloonLayer != null)
		{
			balloonLayer.removeAllRenderables();
		}
	}

	public void createBalloonLayer()
	{
		balloonLayer = new RenderableLayer();
		balloonLayer.setName("Close Control");
		frame.insertBeforePlacenames(frame.getWwd(), balloonLayer);
		frame.getLayerPanel().update(frame.getWwd());
	}

	public void update()
	{
		if (this.offsetTrack != null)
		{
			if (this.offsetTrack instanceof WorldwindTrack)
			{
				WorldwindTrack wwTrack = (WorldwindTrack) this.offsetTrack;
				Position p = wwTrack.getTrackMarker().getPosition();
				Position newPosition = new Position(p.getLatitude(), p.getLongitude(), 2000000);
				this.frame.getWwd().getView().stopMovement();
				this.frame.getWwd().getView().stopAnimations();
				this.frame.getWwd().getView().setEyePosition(newPosition);
				this.frame.getWwd().redraw();
			}
		}
	}

}

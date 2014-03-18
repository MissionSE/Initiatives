package com.missionse.trackplugin;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Vector;

import com.missionse.graphicplugin.LatLongPoint;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.UserFacingIcon;
import gov.nasa.worldwind.render.WWIcon;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;
import gov.nasa.worldwind.render.markers.MarkerAttributes;

public class WorldwindTrack extends RandomTrack
{
	Marker trackMarker;
	WWIcon trackIcon;
	Vector<Marker> historyMarkers;
	MarkerAttributes trackMarkerAttributes;
	MarkerAttributes lastTrackMarkerAttributes;
	MarkerAttributes historyMarkerAttributes;
	int lastDirection;

	public WorldwindTrack()
	{
		this.historyMarkers = new Vector<Marker>();
		createTrackDisplay();
	}

	public void display()
	{
		if (trackMarker == null)
		{
			createTrackDisplay();
		}
	}

	public void displayHistory()
	{
		createHistoryDisplay();
	}

	private void createHistoryDisplay()
	{
		createHistoryMarkerAttributes();
		createHistoryMarkers();
	}

	public void update()
	{
		this.updatePosition(); // This test function forces a lat/long update.
		if (trackMarker != null)
		{
			trackMarker.setPosition(Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
		if (trackIcon != null)
		{
			trackIcon.setPosition(Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
	}

	public void hook()
	{
		trackMarkerAttributes.setMaterial(Material.GREEN);
		trackMarkerAttributes.setOpacity(1d);
		trackMarkerAttributes.setMarkerPixels(trackMarkerAttributes.getMarkerPixels() * 2.0);
		trackMarkerAttributes.setMinMarkerSize(trackMarkerAttributes.getMinMarkerSize() * 2.0);
		trackMarker.setAttributes(trackMarkerAttributes);
	}

	public void unhook()
	{
		System.out.println("Unhooking track" + this.getTrackID());
		createTrackMarkerAttributes();
		trackMarker.setAttributes(trackMarkerAttributes);
	}

	public int getHashCode()
	{
		if (trackMarker != null)
		{
			return trackMarker.hashCode();
		}
		return -1;
	}

	public Marker getTrackMarker()
	{
		return this.trackMarker;
	}

	public WWIcon getTrackIcon()
	{
		return this.trackIcon;
	}

	public Vector<Marker> getTrackHistoryMarkers()
	{
		return this.historyMarkers;
	}

	private void createTrackDisplay()
	{
		createTrackMarkerAttributes();
		createTrackMarker();
		createTrackIcon();
	}

	private void createTrackMarkerAttributes()
	{
		if (this.getAlignment() == "HOSTILE")
		{
			trackMarkerAttributes = new BasicMarkerAttributes(Material.RED, BasicMarkerShape.SPHERE, 1d, 5, 5);
		}
		else if (this.getAlignment() == "NEUTRAL")
		{
			trackMarkerAttributes = new BasicMarkerAttributes(Material.YELLOW, BasicMarkerShape.SPHERE, 1d, 5, 5);
		}
		else if (this.getAlignment() == "FRIEND")
		{
			trackMarkerAttributes = new BasicMarkerAttributes(Material.BLUE, BasicMarkerShape.SPHERE, 1d, 5, 5);
		}
		else if (this.getAlignment() == "UNKNOWN")
		{
			trackMarkerAttributes = new BasicMarkerAttributes(Material.BLACK, BasicMarkerShape.SPHERE, 1d, 5, 5);
		}
		else
		{
			trackMarkerAttributes = new BasicMarkerAttributes(Material.BLACK, BasicMarkerShape.SPHERE, 1d, 5, 5);
		}
	}

	private void createHistoryMarkerAttributes()
	{
		historyMarkerAttributes = new BasicMarkerAttributes(Material.WHITE, BasicMarkerShape.SPHERE, 1d, 1, 1);
	}

	private void createHistoryMarkers()
	{
		Iterator<LatLongPoint> it = historyPoints.iterator();
		this.historyMarkers.clear();
		while (it.hasNext())
		{
			LatLongPoint ll = it.next();
			this.historyMarkers.add(new BasicMarker(Position.fromDegrees(ll.getLatitude(), ll.getLongitude(), 0),
					historyMarkerAttributes));
		}
	}

	private void createTrackMarker()
	{
		this.trackMarker = new BasicMarker(
				Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()), trackMarkerAttributes);
		setTrackMarkerPositionAndHeading();
	}

	private void createTrackIcon()
	{
		if (this.directionalIndicator == 4)
		{
			this.trackIcon = new UserFacingIcon("/home/wolfgang/dev/airplane_iconNE.png", Position.fromDegrees(
					this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
		else if(this.directionalIndicator ==3)
		{
			this.trackIcon = new UserFacingIcon("/home/wolfgang/dev/airplane_iconNW.png", 
					Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
		else if(this.directionalIndicator == 2)
		{
			this.trackIcon = new UserFacingIcon("/home/wolfgang/dev/airplane_iconSE.png", 
					Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
		else
		{
			this.trackIcon = new UserFacingIcon("/home/wolfgang/dev/airplane_iconSW.png", 
					Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		}
		Dimension size = new Dimension(25, 25);
		this.trackIcon.setSize(size);
	}

	private void setTrackMarkerPositionAndHeading()
	{
		trackMarker.setPosition(Position.fromDegrees(this.getLatitude(), this.getLongitude(), this.getAltitude()));
		trackMarker.setHeading(Angle.fromDegrees(this.getLatitude() * 5));
	}

	public void updateDirection()
	{
		super.updateDirection();
		if (this.lastDirection != this.directionalIndicator)
		{
			this.lastDirection = this.directionalIndicator;
			updateTrackIconSource();
		}
	}
	
	private void updateTrackIconSource()
	{
		if (this.directionalIndicator == 4)
		{
			this.trackIcon.setImageSource("/home/wolfgang/dev/airplane_iconNE.png");
		}
		else if(this.directionalIndicator ==3)
		{
			this.trackIcon.setImageSource("/home/wolfgang/dev/airplane_iconNW.png");
		}
		else if(this.directionalIndicator == 2)
		{
			this.trackIcon.setImageSource("/home/wolfgang/dev/airplane_iconSE.png");
		}
		else
		{
			this.trackIcon.setImageSource("/home/wolfgang/dev/airplane_iconSW.png");
		}
	}
}

package com.mse.trackhooknotifier;

import java.util.Enumeration;
import java.util.Map;
import com.solipsys.tdf.track.TrackDatabase;
import com.solipsys.tdf.track.TrackList;
import com.solipsys.model.AbstractModel;
import com.solipsys.model.Model;
import com.solipsys.system.Environment;
import com.solipsys.system.Environment.DynamicModule;
import com.solipsys.util.resource.ResourceManager;

import com.solipsys.tdf.track.number.TrackNumberList;
import com.solipsys.tdf.test.client.TestTrackNumber;

import com.solipsys.tdf.track.Track;
import com.solipsys.tdf.track.report.TrackReport;
import com.solipsys.tdf.track.Identity;
import com.solipsys.tdf.track.Platform;
import com.solipsys.tdf.track.Category;
import com.solipsys.geo.coordinate.Latitude;
import com.solipsys.geo.coordinate.Longitude;
import com.solipsys.geo.coordinate.UnknownLatitudeException;
import com.solipsys.geo.coordinate.UnknownLongitudeException;
import com.solipsys.unit.Degrees;
import com.solipsys.unit.MilesPerHour;
import com.solipsys.unit.Speed;
import com.solipsys.unit.Kilofeet;
import com.solipsys.unit.Radians;
import com.solipsys.unit.Angle;
import com.solipsys.view.Representation;

import com.solipsys.view.Selectable;
import com.solipsys.view.View;
import com.solipsys.view.event.ViewSelectEvent;
import com.solipsys.view.event.ViewSelectListener;


public class TrackHookNotifier 
    implements ViewSelectListener 
{
    protected String name="TrackHookNotifier";

    protected String priHookNum=null;
    protected String secHookNum=null;
    protected TrackDatabase trackDB = TrackDatabase.getDefault();

    public TrackHookNotifier() {
	
    }
    
    public void deselectionOccurred(ViewSelectEvent notice) {
	System.out.println("TrackHookNotifier::deselectionOccurred");

	int selectionID = notice.getSelectionID(0);
	System.out.println("Selection ID: " + notice.getSelectionID(0));

	if (selectionID == 0) {
	    priHookNum = null;
	}
	if (selectionID == 1) {
	    secHookNum = null;
	}
    }

    public void selectionOccurred(ViewSelectEvent notice)  {
	try {
	    System.out.println("TrackHookNotifier::selectionOccurred");

	    Selectable selected = notice.getSelection(0);
	    System.out.println("Selection ID: " + notice.getSelectionID(0));
	    int selectionID = notice.getSelectionID(0);

	    if (selected instanceof Representation) {
		Object model = ((Representation) selected).getModel();
	    
		if (model instanceof TrackNumberList) {
		    Enumeration en = ((TrackNumberList) model).getTrackNumbers();

		    if ( en.hasMoreElements() ) {
			Object obj = en.nextElement();

			if (selectionID == 0) {
			    priHookNum = obj.toString();
			}

			if (selectionID == 1) {
			    secHookNum = obj.toString();
			}
		    }

		    if (priHookNum != null) {
			System.out.println("PRI HOOK: " + priHookNum);
			TestTrackNumber priTrackNum = new TestTrackNumber(priHookNum);
			TrackReport tr = (TrackReport)trackDB.getTrack(priTrackNum);
			System.out.println("Primary Hook: " + tr.toString());
			System.out.println("ID: " + Identity.getIdentity(tr.getIdentity(), false).toString());
			System.out.println("Cat: " + Category.convertValue(tr.getCategory()).getName());
			System.out.println("Plat: " + Platform.getName(tr.getCategory(), tr.getPlatform()));
			System.out.println("Lat: " + Latitude.getLatitudeString(tr.getLatitude(), new Degrees())); 
			System.out.println("Lon: " + Longitude.getLongitudeString(tr.getLatitude(), new Degrees())); 
			System.out.println("Spd: " + tr.getSpeed(new MilesPerHour()) + " mph.");
			System.out.println("Alt: " + tr.getAltitude(new Kilofeet()) + " kft.");
			System.out.println("Crs: " + tr.getCourse(new Degrees()));
		    }
		
		    if (secHookNum != null) {
			System.out.println("SEC HOOK: " + secHookNum);
			TestTrackNumber secTrackNum = new TestTrackNumber(secHookNum);
			TrackReport tr = (TrackReport)trackDB.getTrack(secTrackNum);
			System.out.println("Secondary Hook: " + tr.toString());
			System.out.println("ID: " + Identity.getIdentity(tr.getIdentity(), false).toString());
			System.out.println("Cat: " + Category.convertValue(tr.getCategory()).getName());
			System.out.println("Plat: " + Platform.getName(tr.getCategory(), tr.getPlatform()));
			System.out.println("Lat: " + Latitude.getLatitudeString(tr.getLatitude(), new Degrees())); 
			System.out.println("Lon: " + Longitude.getLongitudeString(tr.getLatitude(), new Degrees())); 
			System.out.println("Spd: " + tr.getSpeed(new MilesPerHour()) + " mph.");
			System.out.println("Alt: " + tr.getAltitude(new Kilofeet()) + " kft.");
			System.out.println("Crs: " + tr.getCourse(new Degrees()));
		    }
		}
	    }
	}
	catch(Exception e ) 
	{
	    System.out.println("Make the damn code compile!");
	}
    }



}

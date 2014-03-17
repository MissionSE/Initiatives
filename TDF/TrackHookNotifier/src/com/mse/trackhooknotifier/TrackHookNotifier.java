package com.mse.trackhooknotifier;

import java.util.Enumeration;

import com.solipsys.tdf.test.client.TestTrackNumber;
import com.solipsys.tdf.track.TrackDatabase;
import com.solipsys.tdf.track.number.TrackNumberList;
import com.solipsys.tdf.track.report.TrackReport;
import com.solipsys.view.Representation;
import com.solipsys.view.Selectable;
import com.solipsys.view.event.ViewSelectEvent;
import com.solipsys.view.event.ViewSelectListener;


public class TrackHookNotifier 
    implements ViewSelectListener 
{
    protected String name="TrackHookNotifier";

    protected String priHookNum=null;
    protected String secHookNum=null;
    protected TrackDatabase trackDB = TrackDatabase.getDefault();

    public TrackHookNotifier(){ 
	
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
		    @SuppressWarnings("rawtypes")
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
			TestTrackNumber priTrackNum = new TestTrackNumber(priHookNum);
			TrackReport tr = (TrackReport)trackDB.getTrack(priTrackNum);
			TrackData priTrack = new TrackData(priHookNum, tr);
			System.out.println (priTrack.toString());
		    }
		
		    if (secHookNum != null) {
			TestTrackNumber secTrackNum = new TestTrackNumber(secHookNum);
			TrackReport tr = (TrackReport)trackDB.getTrack(secTrackNum);
			TrackData secTrack = new TrackData(secHookNum, tr);
			System.out.println(secTrack.toString());
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

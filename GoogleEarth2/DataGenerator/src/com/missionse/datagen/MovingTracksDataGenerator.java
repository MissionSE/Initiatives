package com.missionse.datagen;

import java.util.ArrayList;
import java.util.Random;

import com.missionse.GEUtils.TrackPosition;
import com.missionse.dbutils.DatabaseAccess;

public class MovingTracksDataGenerator extends Thread {

	// When the number of tracks gets too high track history becomes a 
	// single point for each track.
	private int num_tracks = 100; //Integer.MAX_VALUE/1000;
	private DatabaseAccess db = null;
	private int update_delay = 100; // milliseconds
	private Random rand = new Random();
	
	private class Track {
		public int id;
		public TrackPosition pos;
		
		public Track (int id, TrackPosition pos) {
			this.id = id;
			this.pos = pos;
		}
	}
	private ArrayList<Track> tracklist;
	
	private void initializeTracks() {
		
		
		// Simulate N tracks moving in different directions.
	
		TrackPosition tp = new TrackPosition(48.2, 16.3, 40000, 0);
	
		// They all start at the same position.
		for (int i =0; i<num_tracks; i++) {
			tracklist.add(new Track(i, tp));
		}
	}

	MovingTracksDataGenerator() {
		db = new DatabaseAccess();
		
		// initialize tracks
		tracklist = new ArrayList<Track>();
		initializeTracks();
	}

	@Override
	public void run() {
		generateData();
	}

	private int randomSign() {
		return rand.nextDouble() >= .55 ? 1 : -1;
		//return (Math.random() >= .5) ? 1 : -1;
	}
	private int randomHeading() {
		//return (int)(Math.random() * 360);
		return (int) (rand.nextDouble() * 360);
	}
	
	
	private void moveTrack(Track t) {
		
		int sign = (int)Math.random() >= .5 ? -1 : 1;
		double lat_delta = Math.random() + .0000001 / 100 * sign;
		double lng_delta = Math.random() + .0000001 / 100 * sign;
		double alt_delta = Math.random() + .0000001 / 100 * sign;
		
		if (t.pos.lat + lat_delta > 90) {
			t.pos.lat = 90;
		} else if (t.pos.lat < -90) {
			t.pos.lat = -90;
		} else {
			t.pos.lat += lat_delta;
		}
		
		if (t.pos.lng + lng_delta > 180) {
			t.pos.lng = 180;
		} else if (t.pos.lng < -180) {
			t.pos.lng = -180;
		} else {
			t.pos.lng += lng_delta;
		}

		if (t.pos.alt + alt_delta > 35000) {
			t.pos.alt -= alt_delta*5;
		} else if (t.pos.alt < 15000) {
			t.pos.alt += alt_delta*5;
		} else {
			t.pos.alt += alt_delta;
		}
		
		t.pos.alt *= alt_delta * sign;
		t.pos.heading = (int)(Math.random() * 360);
	}
	private double createDelta() {
		double rand = Math.random();
		//rand = (rand == 0.0 ? 0.0001 : rand)/100 * randomSign();
		//rand = ((rand == 0.0 ? rand : rand)) * randomSign();
		rand = rand * randomSign();
		
		//double r = rand.nextDouble();
		//r = (r == 0.0 ? r : r/100);
		//System.out.println("rand = " + rand);
		return rand;
	}
	
	private void generateData() {

		// clean out any old entries in the database
		db.clearTrackPositionData();
		db.clearTrackHistoryData();
		
		while (true) {
			// This could have also been done by putting
			// the db update function on a interval time.
			try {
				// begin periodically updating the database with new data.
				Thread.sleep(update_delay);
				for (Track t : tracklist) {
					
					int sign = randomSign();
					double lat_delta = createDelta();// * sign;
					double lng_delta = createDelta();// * sign;
					double alt_delta = 0;
					
					//moveTrack(t);
					
					t.pos.add(lat_delta, lng_delta, alt_delta, 0);
					//System.out.printf("track pos(%f,  %f, %f, %d)\n", 
					//t.pos.lat, t.pos.lng, t.pos.alt, t.pos.heading);
					db.updateTrackPosition(t.id, t.pos.lat, t.pos.lng, t.pos.alt, t.pos.heading);
					db.addHistoryPosition(t.id, t.pos.lat, t.pos.lng, t.pos.alt);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

package com.mse.trackhooknotifier;

import com.google.gson.Gson;

public class TrackDataWriter  {
    private static Gson gson = new Gson();
    private static SqlWriter sqlWriter = new SqlWriter("jdbc:sqlite:/tmp/tdfTracks.db");

    public static String writeJson(TrackData td) {
	
	String jsonTrack=gson.toJson(td);
	System.out.println(jsonTrack);
	return jsonTrack;
    } 

    public static TrackData readJson(String jsonTrack) {

	TrackData td = gson.fromJson(jsonTrack, TrackData.class);
	System.out.println(td.toString());
	return td;
    }
    public static boolean writeSql(TrackData td) {
	
	return sqlWriter.executeTrackWrite(td);
    }
}


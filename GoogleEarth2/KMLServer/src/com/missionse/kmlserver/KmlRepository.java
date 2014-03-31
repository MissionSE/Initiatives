package com.missionse.kmlserver;

import java.util.HashMap;
import java.util.Map;

public class KmlRepository {
	// TODO: protect with a lock
	static Map<String,String> repository = new HashMap<String, String>();

	KmlRepository() {
	}
	
	public static void add(String key, String kml) {
		repository.put(key, kml);
		//System.out.println("Added " + key + " to repository");
	}
	
	public static String get (String key) {
		//System.out.println("returning " + key + " from repository");
		return repository.get(key);
	}
	
	public static String remove(String key) {
		return repository.remove(key);
	}
}

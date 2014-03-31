package kml;

import com.missionse.GEUtils.ObjectCache;

public class KmlCache {
	
	ObjectCache<KmlId> cache;
	
	public String get(KmlId id) {
		
		// Attempt to get the KML object from the cache.
		String kmlString = (String)cache.get(id);
		if (kmlString != null) {
			return kmlString;
		}

		// If it isn't there create one 
		// TODO: Create a Kml package.
		// It should contain the KmlUtil functions
		// KmlCache and a KmlStringCreator(id) or some method that will get the
		// data from the datbase and turn it into a kml string.
		kmlString = KmlString.createKmlString(id);
		if (kmlString == null) {
			// Failed to create a string or find one in the cache
			return null;
		}
		
		//add it to the cache
		cache.put(kmlString, id, id.duration);
		
		return kmlString;
		
	}
}

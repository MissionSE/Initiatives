package com.missionse.GEUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// TODO synchronize access
public class ObjectCache<CacheKeyType>{

	class CacheObject {
		
		public CacheObject (Object o, long duration) {
			this.o = o;
			this.expires = Calendar.getInstance().getTimeInMillis() + duration;
		}
		
		public Object o;
		public long expires;
		
	}
	
	public ObjectCache() { }
	
	// String isn't a good key, string objects are huge and slow to create.
	public void put (Object o, CacheKeyType key, long duration) {
		CacheObject co = new CacheObject(o, duration);
		cache.put(key, co);
		
	}
	
	public Object get (CacheKeyType key) {
		Object o = null;
		CacheObject co = cache.get(key);
		// If we found something make sure it hasn't expired.
		if (co != null && co.expires >= Calendar.getInstance().getTimeInMillis()) {
			cache.remove(key);
		} else {
			o = co.o;
		}
			
		return o;
	}
	
	// Cache
	Map<CacheKeyType, CacheObject> cache = new HashMap<CacheKeyType, CacheObject>();
	
}
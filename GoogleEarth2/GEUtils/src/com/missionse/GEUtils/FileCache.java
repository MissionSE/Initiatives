package com.missionse.GEUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
// TODO: make a factory so we can support dependency injection.
public class FileCache {

	// Use WatchService to monitor files for changes and then either drop them from the cache
	// or refresh the cache.

	// maps pathname to file.
	private static Map<String, File> fileCache = new ConcurrentHashMap<String, File>();
	private static Object cacheFileLock;

	public FileCache() {

	}

	static public File get(String path) {
		File file = fileCache.get(path);
		if (null == file) {
			System.out.println("Caching" + path);
			file = cacheFile(path); // returns null if file can't be read
		} else {
			System.out.println(path + "retrieved from cache");
		}

		return file;
	}

	private static File cacheFile (String path) {
		// It would be even nicer if we just prevented multiple attempts
		// to cache the same file rather then making all caching sequential.
		synchronized(cacheFileLock){
			// read file from disk
			// add it to the map and then return it.
			File file = new File(path);
			fileCache.put(path, file);
			return file;
		}
	}
}

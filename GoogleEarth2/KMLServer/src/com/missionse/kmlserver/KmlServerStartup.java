package com.missionse.kmlserver;

public class KmlServerStartup {

	static final String BASE_URI = "http://localhost:9999/";
	
	// Runs from the command line.
	// Lists for add request on 9999
	// Exists when any input is received. 
	public static void main(String[] args){
		
		// Start the DBMonitor thread
		new KmlServerDatabaseMonitor().start();  // Start the DatabaseMonotor
	
		new HTTPServer(BASE_URI).start(); // Start the HTTP server.
	}
}

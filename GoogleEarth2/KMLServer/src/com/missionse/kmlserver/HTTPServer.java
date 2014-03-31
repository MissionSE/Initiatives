package com.missionse.kmlserver;


import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

// Removed from the api
//import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class HTTPServer extends Thread {
	final String base_uri;

	HTTPServer(String base_uri) {
		this.base_uri = base_uri;
	}
	
	public void run() {
		try {
			startServer();
		} finally {
			System.out.println("HTTPServer exiting");
		}
	}

	private void startServer() {	
		try {
			HttpServer server = null;
			
			// Add the REST provider
			// Add the CORS provider, to allow request from remote browsers.
			ResourceConfig rc = new ResourceConfig(KmlServer.class, CORSFilter.class);
			
			URI endpoint = new URI(base_uri);
			
			server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
			
			System.out.println("HttpServer running at " + base_uri);
			System.out.println("Press Enter to stop the server.");
			System.in.read();
			server.stop(0);
			System.out.println("Server stopped");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println("HttpServer terminated, program exiting.");
			Runtime.getRuntime().halt(0);
		}
		
		
	}
}

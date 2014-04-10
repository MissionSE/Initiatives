package com.missionse.kmlserver;

import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;


import java.util.List;


// For JAX-RS/REST
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import de.micromata.opengis.kml.v_2_2_0.Kml;

import com.missionse.GEUtils.KmlUtil;
import com.missionse.GEUtils.ResponseUtil;
import com.missionse.dbutils.DatabaseAccess;

//@Path means root resource class or method
// Sets the path to base URI+string
// When used on a method the string is catted on to the class path.
@Path("/example")
public class KmlServer {

	@Context UriInfo uri; // Always the uri currently being processed?

	private int response_delay = 2000; // 2 seconds

	private DatabaseAccess db;

	public KmlServer() {
		db = new DatabaseAccess();
	}

	// BROKEN
	// localhost:nnnn/example/kml/longpoll
	//@GET @Path("/kml/xxx")
	//@Produces (MediaType.TEXT_XML)
	public String getLogetngPoll() {
		//System.out.println("getLongPoll called");
		try {
			KmlUtil kmlUtil = new KmlUtil();
			Kml kml = kmlUtil.getNextTrackLocation(); // simulate moving track
			String kmlString = KmlUtil.toString(kml);

			KmlUtil.toFile(kml,  "longpoll.kml");

			kml.marshal();

			return kmlString;
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;

	}
	// localhost:nnnn/example/kml/trackhistory/<trackid>
	@GET @Path("/kml/trackhistory/{trackid}")
	@Produces(MediaType.TEXT_XML)
	public String getTrackHistoryKml(@PathParam("trackid") String trackid) {
		//System.out.println("getTrackHistoryKml called");
		// Simulate delayed update
		try {
			Thread.sleep(response_delay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String kmlString = null;
		try {
			// Get flight history path for trackid sorted by "time"
			ResultSet resultSet = db.getTrackHistory(trackid);
			if (resultSet == null || resultSet.isBeforeFirst() == false) {
				//System.out.println("No results");
				return null;
			}

			// Convert results to KML
			KmlUtil kmlUtil = new KmlUtil();
			Kml kml = kmlUtil.convertTrackHistory(resultSet);
			kmlString = KmlUtil.toString(kml);

			KmlUtil.toFile(kml,  "trackhistory.kml"); // for debug
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kmlString;
	}
	// TODO: take optional filename argument geo/<filename>
	// localhost:nnnn/example/kml/geo
	@GET @Path("/kml/geo")
	@Produces(MediaType.TEXT_XML)
	public String getGeoPointsKml() {

		String kmlString = null;

		try{


			ResultSet resultSet = db.getGeoPoints();
			if (resultSet == null || resultSet.isBeforeFirst() == false) {
				//System.out.println("No results");
				return "";
			}
			// convert points to KML
			KmlUtil kmlUtil = new KmlUtil();
			Kml kml = kmlUtil.convertGeoPoints(resultSet); // should be static or inherit from Kml
			kmlString = KmlUtil.toString(kml);

			// Write kml to a file for debugging.
			KmlUtil.toFile(kml, "geopoints.kml");

		}catch (Exception e){
			e.printStackTrace();
		}
		return kmlString;
	}

	// localhost:nnnn/example/geo
	@GET @Path("/geo")
	@Produces(MediaType.TEXT_PLAIN)
	public String getGeoPoints() {

		// TODO: needs the if-modified-since processing
		String kmlString = null;

		try{
			ResultSet resultSet = db.getGeoPoints();
			if (resultSet == null || resultSet.isBeforeFirst() == false) {
				//System.out.println("No results");
				return "";
			}
			// convert points to KML
			KmlUtil kmlUtil = new KmlUtil();
			Kml kml = kmlUtil.convertGeoPoints(resultSet); // should be static or inherit from Kml
			kmlString = KmlUtil.toString(kml);
		}catch (Exception e){
			e.printStackTrace();
		}
		return kmlString;
	}

	/*
	// localhost:nnnn/example/db
	@GET @Path("/db")
	@Produces(MediaType.TEXT_PLAIN)
	public String dbExample() {
		try {

			// create connection to the MySQL database
			String host = "localhost";
			String database = "feedback";
			String user = "mseuser";
			String password = "mseuser";
			db.connect(host, database, user, password);

			//System.out.println("dbExample called");
			return db.readDatabase();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return "dbExample reached end";
	}

	// @GET - invoked as response to GET request
	// @Produces - returned MIME types, text, xml, json &tc.
	@GET
	@Path("/add/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlainText(@PathParam("a")double a, @PathParam("b") double b){
		try {
		//System.out.println("addPlainText(" + a + "," + b + ") called");

		final Kml kml = new Kml();

		kml.createAndSetPlacemark().withName("London, UK").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(-0.126236, 51.500152);
		// marshals to console
		kml.marshal();

		// marshal to output stream and then convert to a string.
		ByteArrayOutputStream kmlstream = new ByteArrayOutputStream();
		kml.marshal(kmlstream);
		String kmlstring = new String(kmlstream.toString());

		// save it in a file for good measure
		kml.marshal(new File("London.kml"));

		return kmlstring;

		} catch (Exception e) {
			e.printStackTrace();
		}		
		//return (a+ b) + "";
		return "shouldn't get here";
	}

	 */

	private static final String track_icon_path = "C:\\Documents and Settings\\mseuser\\eclipse\\KmlServer\\trackicon.png";
	private static final String city_icon_path = "C:\\Documents and Settings\\mseuser\\eclipse\\KmlServer\\cityicon.png";

	private enum IconType{
		TRACK_ICON(track_icon_path),
		CITY_ICON(city_icon_path);

		private String filepath;
		private IconType(String filepath) {
			this.filepath = filepath;
		}
		private String getFilePath() {
			return this.filepath;
		}
	}

	// TODO: change the png file name to a variable.
	// Only accept certain values.
	@GET @Path("/images/cityicon.png")
	@Produces("image/png")
	public Response getCityIcon () {
		System.out.println("request for cityicon");
		Response response = getIconResponse(IconType.CITY_ICON);
		return response;
	}
	@GET @Path("/images/trackicon.png")
	@Produces("image/png")
	public Response getTrackIcon () {
		System.out.println("request for track icon");
		Response response = getIconResponse(IconType.TRACK_ICON);
		return response;
	}

	private Response getIconResponse (IconType type) {
		//System.out.println("called getIconResponse()");

		// TODO: Support If-Modified-Since
		// Cache the file so we only read it once.

		//File file = FileCache.get(type.getFilePath());
		// This doesn't really make sense because a File object
		// doesn't contain the contents of the file. Caching the contents
		// of the file may be a better idea.
		File file = new File(type.getFilePath());
		if (file.canRead() == false) {
			System.out.println(type.getFilePath() + "is unreadable");
			return Response.noContent().build(); // is this the correct response?
		}

		ResponseBuilder builder = Response.ok(file);
		//ResponseUtil.addCacheTimeout(builder, 86400);
		return builder.build();
	}

	@GET @Path("kml/circles/{type}")
	@Produces (MediaType.TEXT_XML)
	public String getCircles (@PathParam("type") int type) {
		String kmlString = null;
		try {
			ResultSet resultSet = db.getCircles(type);
			if (resultSetEmpty(resultSet)) {
				//System.out.println("resultSet empty");
				return null;
			}

			KmlUtil kmlUtil = new KmlUtil();
			Kml kml = kmlUtil.convertCircles(resultSet);
			kmlString = KmlUtil.toString(kml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return kmlString;
	}

	@GET @Path("/kml/gxtrack")
	@Produces (MediaType.TEXT_XML)
	public String getGxTrackFile() {
		//System.out.println("getGxTrackFile() called");

		try {
			File gxtrack_file = new File("phl_to_wien.kml");
			//File gxtrack_file = new File("gxtrack.kml");
			byte[] buf = Files.readAllBytes(gxtrack_file.toPath());
			return new String(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@GET @Path("/kml/findtrack/{trackid}")
	@Produces (MediaType.TEXT_XML)
	public String findTrack(@PathParam("trackid") int trackid) {
		String kmlString = null;
		try{
			ResultSet resultSet = db.getTrackPosition(trackid);
			if (resultSetEmpty(resultSet)) {
				//System.out.println("resultSet empty");
				return null;
			}

			// convert points to KML
			//Kml kml = kmlUtil.convertLookAtTrack(resultSet); // should be static or inherit from Kml
			Kml kml = KmlUtil.convertTrackPosition(resultSet); // should be static or inherit from Kml
			kmlString = KmlUtil.toString(kml);
			// TODO: Return a Response instead. Is that better for any reason?
			// TODO: Add KmlUtil::toResponse(kml)
		}catch (Exception e){
			e.printStackTrace();
		}

		return kmlString;

	}

	public boolean resultSetEmpty (ResultSet resultSet) {
		try {
			return resultSet == null || resultSet.isBeforeFirst() == false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//For some reason the kml prefix started causing problems for the 
	// browser, the server wasn't receiving the requests anymore. Removing
	// /kml was the only way I found to fix it.
	//@GET @Path("/kml/trackposition")
	// Whirl Wind needs a .kml extension if you type the path into
	// the url dialog box. This is going to be a problem for urls that
	// take arguments from the path.
	@GET @Path("/trackposition")
	@Produces (MediaType.TEXT_XML)
	public String getTrackPosition(@QueryParam("options") List<String>options) {
		
		// The only option supported now is "labels"
		if (options.contains("labels")) {
			System.out.println("Request for trackposition with labels");
		}

		
		//System.out.println("getTrackPosition called");
		try {
			//System.out.println("track position sleeping..");
			Thread.sleep(response_delay);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String kmlString = getTrackPositionKmlString();
		//String kmlString = getTrackPositionAsKmlString(-1);
		
		return kmlString;
	}

	private String getTrackPositionKmlString() {
		return KmlRepository.get("trackposition");
	}
}


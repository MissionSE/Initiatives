package com.missionse.GEUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LabelStyle;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Style;

public class KmlUtil {

	// TODO these function should be static or this
	// class should inherit from kml and work on the
	// this object.

	public Kml convertTrackHistory (ResultSet resultSet) {

		final Kml kml = new Kml();

		//System.out.println("KmlUtil::convertTrackHistory called");

		if (resultSet == null) {
			//System.out.println("resultSet is null");
			return null;
		}

		final Document document = kml.createAndSetDocument().withName("TrackHistory");
		document.createAndAddStyle()
		.withId("trackhistory_style")
		.createAndSetLineStyle()
		.withColor("7f0000ff")
		.withWidth(4.0);

		LineString lineString = null;
		Double lat = null;
		Double lng = null;
		Double alt = null;
		try {
			boolean first = true;
			while (resultSet.next()) {
				lat = resultSet.getDouble("lat");
				lng = resultSet.getDouble("lng");
				alt = resultSet.getDouble("alt");

				// First time through create the lookat
				if (first == true) {
					// Mark the beginning of the line
					document.createAndAddPlacemark().withName("Start")
					.createAndSetPoint()
					.addToCoordinates(lng,lat,alt);

					// Look at the beginning point.
					document.createAndSetLookAt()
					.withLongitude(lng)
					.withLatitude(lat)
					.withRange(150d)
					.withTilt(50.0d)
					.withHeading(0.0d);

					lineString = document.createAndAddPlacemark()
							.withName("FlightPath")
							.withStyleUrl("#trackhistory_style")
							.createAndSetLineString()
							.withAltitudeMode(AltitudeMode.RELATIVE_TO_GROUND)
							.withExtrude(true)
							.withTessellate(true);
				}
				first = false;

				lineString.addToCoordinates(lng,lat,alt);
			}// end while

			//  Mark the end of the line.
			document.createAndAddPlacemark().withName("End")
			.createAndSetPoint()
			.addToCoordinates(lng,lat,alt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return kml;
	}

	// Move to some shared location
	private static final String city_icon_href = "http://localhost:9999/example/images/cityicon.png";
	public Kml convertGeoPoints(ResultSet resultSet) {
		//System.out.println("KmlUtil:convertGeoPoints called");

		if (resultSet == null) {
			//System.out.println("convertGeoPoints: resultSet is null");
			return null;
		}

		final Kml kml = new Kml(); // The Kml object is the entire thing.
		Document document = kml.createAndSetDocument().withName("Cities").withVisibility(true); // ads document to kml

		Icon icon = new Icon().withHref(city_icon_href);
		IconStyle iconStyle = new IconStyle()
		.withIcon(icon)
		.withScale(1.0);
		Style cityStyle = new Style()
		.withId("cityStyle")
		.withIconStyle(iconStyle);

		try {
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				Double lat = resultSet.getDouble("lat");
				Double lng = resultSet.getDouble("lng");

				// add placemark to the document within kml
				document.createAndAddPlacemark()
				// Add a link to the city's wikipedia page to the database
				// then add the link to the description using CDATA
				.withName(name)
				.addToStyleSelector(cityStyle)
				.createAndSetPoint().addToCoordinates(lng,lat);

				//System.out.println("convertGeoPoints: Added placemark for " + name + " (" + lat + ", " + lng + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return kml;
	}

	public static String toString(Kml kml) {
		String kmlString = null;
		try {
			// marshal to output stream and then convert to a string.
			ByteArrayOutputStream kmlStream = new ByteArrayOutputStream();
			kml.marshal(kmlStream);
			kmlString = new String(kmlStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kmlString;
	}

	public static void toFile(Kml kml, String fileName) {
		try {
			ByteArrayOutputStream kmlStream = new ByteArrayOutputStream();
			kml.marshal(kmlStream);
			OutputStream outputStream = new FileOutputStream(fileName);
			kmlStream.writeTo(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static double moving_track_lat = 48.208300; //-90 to 90
	static double moving_track_lng = 16.373100; //-180 to +180
	static double moving_track_alt = 200000.0;
	static int altitude_sign = -1;
	static int altitude_delta = 100000;

	static double latitude_increment = 0.1;
	static int increment_sign = 1;
	public Kml getNextTrackLocation() {
		try {
			// advance track
			// Once the latitude passes 90, the longitude should switch signs 
			// and we begin to subtract from the latitude.
			double temp_lat = moving_track_lat + increment_sign * latitude_increment;
			if (temp_lat > 90 || temp_lat < -90) {

				// flip the longitude to the other side of the globe
				double lng_deg = 180 - Math.abs(moving_track_lng);
				int sign = (int) Math.signum(moving_track_lng);
				sign = sign == 0 ? 1 : -1;
				moving_track_lng = lng_deg * -1 * sign;

				// change the sign of the increment and recalculate.
				// this isn't exact, the track will jump but its close enough 
				increment_sign *= -1;
				temp_lat = moving_track_lat + increment_sign * latitude_increment;
			}
			moving_track_lat = temp_lat;

			altitude_sign *= -1;
			moving_track_alt = altitude_sign * altitude_delta;

			//System.out.println("New track position is (" + moving_track_lng + ", "
			//	+ moving_track_lat + ", " + moving_track_alt + ")");
			// create placemark
			final Kml kml = new Kml(); // The Kml object is the entire thing.
			Document document = kml.createAndSetDocument()
					.withName("TrackNUpdate") // adds document to kml
					.withId("TrackNUpdate");
			//Document document = kml.createAndSetDocument(); // adds document to kml
			document.createAndAddPlacemark().withName("TrackPosition")
			.createAndSetPoint().addToCoordinates(moving_track_lng, moving_track_lat, moving_track_alt)
			.withExtrude(true).setAltitudeMode(AltitudeMode.RELATIVE_TO_GROUND);

			/* Look at isn't working
			// Move camera to new point
			document.createAndSetLookAt()
			.withLongitude(moving_track_lng)
			.withLatitude(moving_track_lat)
			.withRange(150d)
			.withTilt(50.0d)
			.withHeading(0.0d);
			 */

			return kml;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Kml convertTrackPosition(ResultSet resultSet) {
		final Kml kml = new Kml(); // The Kml object is the entire thing.

		// TODO: this path shouldn't be hardcoded.
		Icon icon = new Icon().withHref("http://localhost:9999/example/images/trackicon.png");

		Document document = kml.createAndSetDocument()
				.withName("TrackFile") // adds document to kml
				.withId("TrackFile");
		 
		try {
			while (resultSet.next()) {

				//String name = resultSet.getString("name");
				int id = resultSet.getInt("trackid");
				Double lat = resultSet.getDouble("lat");
				Double lng = resultSet.getDouble("lng");
				Double alt = resultSet.getDouble("alt");
				int heading = resultSet.getInt("heading");

				// The problem with doing it this way is that the same style object is
				// used for each track. So only the final update before the marshal counts.
				//trackStyle.getIconStyle().setHeading(heading);

				// Each placemark must have it's own IconStyle because it controls
				// the 'heading' of the track icon. This means that each track must
				// have it's own Style which means that it also must have it's own LabelStyle.
				// If it wasn't for the icon heading they could all share a Style and we could
				// turn labels on and off for all tracks by changing the shared Style.
				LabelStyle labelStyle = new LabelStyle()
				.withId("trackLabelStyle" + Integer.toString(id))
				.withScale(0); // labels are hidden by default
				
				IconStyle iconStyle = new IconStyle()
				.withId("trackIconStyle"+ Integer.toString(id))
				.withIcon(icon)
				.withScale(1.0)
				.withHeading(heading);
				
				Style trackStyle = new Style()
				.withId("trackStyle"+ Integer.toString(id))
				.withIconStyle(iconStyle)
				.withLabelStyle(labelStyle);

				//System.out.println("Heading is " + Integer.toString(heading));

				String description = "Id: " + Integer.toString(id) + "\n"
						+ "Lat: " + Double.toString(lat) + "\n"
						+ "Lng: " + Double.toString(lng) + "\n"
						+ "Alt: " + Double.toString(alt) +"\n"
						+ "Heading: " + Integer.toString(heading);
				document.createAndAddPlacemark()
				.withName("Track " + Integer.toString(id))
				.withId(Integer.toString(id))
				.withDescription(description)
				.addToStyleSelector(trackStyle)
				.createAndSetPoint().addToCoordinates(lng, lat, alt)
				.withExtrude(true).setAltitudeMode(AltitudeMode.ABSOLUTE);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		//kml.marshal(); // print to console
		return kml;
	}

	public Kml convertLookAtTrack(ResultSet resultSet) {
		System.out.println("KmlUtil:convertLookAtTrack called");
		if (resultSet == null) {
			System.out.println("resultSet is null");
			return null;
		}

		final Kml kml = new Kml(); // The Kml object is the entire thing.
		Document document = kml.createAndSetDocument().withName("LookAtTrack").withVisibility(true);

		try {
			while (resultSet.next()) {
				//String name = resultSet.getString("name");
				Double lat = resultSet.getDouble("lat");
				Double lng = resultSet.getDouble("lng");

				document.createAndSetLookAt()
				.withLongitude(lng)
				.withLatitude(lat)
				//.withRange(150d)
				//.withTilt(50.0d)
				//.withHeading(0.0d)
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		kml.marshal();
		return kml;
	}

	// TODO: 
	private List<Point> findCirclePoints (double lat, double lng, double alt, Double radius) {
		int num_pts = 36;
		int degrees_per_point = 360 / num_pts;
		int current_angle = 0;

		double x = 0;
		double y = 0;
		double z = 0;

		List<Point> points = new ArrayList<Point>(); // list of Points
		for (int i=0; i<num_pts; i++) {
			double angle_in_radians = Math.toRadians(current_angle);
			x = Math.cos(angle_in_radians) * radius;
			y = Math.sin(angle_in_radians) * radius;
			z = alt;

			Point p = new Point(lat+x,lng+y, z);
			points.add(p);

			current_angle += degrees_per_point;
		}

		return points;
	}

	public Kml convertCircles(ResultSet resultSet) {

		final Kml kml = new Kml(); // The Kml object is the entire thing.
		Document document = kml.createAndSetDocument().withName("Circles").withVisibility(true);
		document.createAndAddStyle().withId("circle_style")
			.createAndSetLineStyle().withWidth(4.0)
									.withColor("ffffffff");

		try {
			while (resultSet.next()) {
				Double lat = resultSet.getDouble("center_lat");
				Double lng = resultSet.getDouble("center_lng");
				Double alt = resultSet.getDouble("center_alt");
				Double radius = resultSet.getDouble("radius");

				// For each point get a list of points creating a circle
				List<Point> points = findCirclePoints(lat,lng, alt, radius);
				System.out.println("Created " + points.size() + " points to define the circle.");
				
				// Create LineString with each of these points.
				LineString lineString = 
						document.createAndAddPlacemark()
							.withName("Circle")
							// TODO create circle style
							.withStyleUrl("#circle_style")
							.createAndSetLineString()
								.withAltitudeMode(AltitudeMode.RELATIVE_TO_GROUND)
								.withExtrude(true)
								.withTessellate(true);
				boolean first_point = true;
				Point lastpoint = null;
				int i = 1;
				for (Point p: points) {
					if (first_point) {
						lastpoint = p;
						first_point = false;
					}
					// TODO: circle points overlap
					lineString.addToCoordinates(p.x, p.y, p.z);
				}
				
				document.createAndAddPlacemark().withName(Integer.toString(i++)); // debug
				lineString.addToCoordinates(lastpoint.x, lastpoint.y, lastpoint.z);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kml;
	}
}

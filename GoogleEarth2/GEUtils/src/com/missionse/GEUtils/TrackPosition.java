package com.missionse.GEUtils;

//import static java.lang.System.out.printf;

public class TrackPosition {
	
	public double lat;
	public double lng;
	public double alt;
	public int heading;
	
	private int lat_sign;
	private int lng_sign;
	
	public TrackPosition(double lat, double lng, double alt, int heading) {
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.heading = heading;
		
		lat_sign = (int)Math.signum(lat);
		lng_sign = (int)Math.signum(lng);
	}

	// TODO: moveto(lat, lng, alt);
	
	public void add (double lat_delta, double lng_delta, double alt_delta, int heading) {
		
		this.heading = (int)(360 * Math.random());
		
		// LATITUDE
		// Once the absolute value of the LATITUDE passes 90, the LONGITUDE should switch signs 
		// and we begin to subtract from the LATITUDE.
		double temp_lat = this.lat + this.lat_sign * lat_delta;
		if (Math.abs(temp_lat) > 90 ) {
			// flip the longitude to the other side of the globe
			double lng_deg = 180 - Math.abs(this.lng);
			int sign = (int) Math.signum(this.lng);
			sign = sign == 0 ? 1 : -1;
			this.lng = lng_deg * -1 * sign;
			
			// TODO: this isn't correct.
			// Now change the sign of the increment and recalculate.
			// this isn't exact, the track will jump but its close enough 
			this.lat_sign *= -1;
			temp_lat = this.lat + this.lat_sign * lat_delta;
		}
		this.lat = temp_lat;
		
		// LOGNITUDE
		// When the longitude reaches 180, flip the sign.
		double temp_lng = this.lng + this.lng_sign * lng_delta;
		if (Math.abs(temp_lng) > 180) {
			this.lng *= -1;
			this.lng_sign *= -1;
			temp_lng = this.lng + this.lng_sign + lng_delta;
		}
		this.lng = temp_lng;
		
		// Altitude
		this.alt += alt_delta;
		
		//System.out.printf("add: lat=%f,  lng=%f, alt=%f\n", this.lat, this.lng, this.alt);
	}
}

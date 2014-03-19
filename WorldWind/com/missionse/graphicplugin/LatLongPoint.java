package com.missionse.graphicplugin;

public class LatLongPoint {
	private double latitude;
	private double longitude;

	LatLongPoint() {
		this.latitude = 0;
		this.longitude = 0;
	};

	public LatLongPoint(double latitude, double longitude) {
		if (!setLatLong(latitude, longitude)) {
			this.latitude = 0;
			this.longitude = 0;
		}
	}

	public double getLatitude() {
		return this.latitude;
	};

	public double getLongitude() {
		return this.longitude;
	};

	public double getLatitudeRadians() {
		return ((this.latitude * Math.PI) / 180);

	};

	public double getLongitudeRadians() {
		return ((this.longitude * Math.PI) / 180);
	};

	public Boolean setLatitude(double latitude) {
		if (latitude >= -90 && latitude <= 90) {
			this.latitude = latitude;
			return true;
		}
		return false;
	};

	public Boolean setLongitude(double longitude) {
		if (longitude >= -180 && longitude <= 180) {
			this.longitude = longitude;
			return true;
		}
		return false;
	};

	public Boolean setLatLong(double latitude, double longitude) {
		double lastLatitude = this.latitude;
		double lastLongitude = this.longitude;
		if (setLatitude(latitude) && setLongitude(longitude)) {
			return true;
		} else {
			setLatitude(lastLatitude);
			setLongitude(lastLongitude);
			return false;
		}
	};

	public Boolean isValid() {
		if (latitude >= -90 && latitude <= 90 && longitude >= -180
				&& longitude <= 180) {
			return true;
		} else {
			return false;
		}
	}

}

package com.mse.closecontrol;

public class TrackData {

    public static final int PRI_HOOK = 0;
    public static final int SEC_HOOK = 1;

    private int hookType;
    private String trackNumber;
    private String identity;
    private String category;
    private String platform;
    private String latitude;
    private String longitude;
    private String speed;
    private String altitude;
    private String course;

    public TrackData() {
    }

    public TrackData(int hookType, String tn, String id, String cat, String plat, String lat, String lon, String spd,  String alt, String crs) {
	this.hookType = hookType;
	this.trackNumber = tn;
	this.identity = id;
	this.category = cat;
	this.platform = plat;
	this.latitude = lat;
	this.longitude = lon;
	this.speed = spd;
	this.altitude = alt;
	this.course = crs;

    }

    public String toString() {
	return "TrackData [trackNumber=" + trackNumber + ", identity="
	    + identity + ", category=" + category + ", platform="
	    + platform + ", latitude=" + latitude + ", longitude="
	    + longitude + ", speed=" + speed + ", altitude=" + altitude
	    + ", course=" + course + "]";
    }

    public int getHookType() {
	    return hookType;
    }

    public void setHookType(int hookType) {
	this.hookType = hookType;
    }

    public String getTrackNumber() {
	return trackNumber;
    }
    public void setTrackNumber(String trackNumber) {
	this.trackNumber = trackNumber;
    }
    public String getIdentity() {
	return identity;
    }
    public void setIdentity(String identity) {
	this.identity = identity;
    }
    public String getCategory() {
	return category;
    }
    public void setCategory(String category) {
	this.category = category;
    }
    public String getPlatform() {
	return platform;
    }
    public void setPlatform(String platform) {
	this.platform = platform;
    }
    public String getLatitude() {
	return latitude;
    }
    public void setLatitude(String latitude) {
	this.latitude = latitude;
    }
    public String getLongitude() {
	return longitude;
    }
    public void setLongitude(String longitude) {
	this.longitude = longitude;
    }
    public String getSpeed() {
	return speed;
    }
    public void setSpeed(String speed) {
	this.speed = speed;
    }
    public String getAltitude() {
	return altitude;
    }
    public void setAltitude(String altitude) {
	this.altitude = altitude;
    }
    public String getCourse() {
	return course;
    }
    public void setCourse(String course) {
	this.course = course;
    }
}

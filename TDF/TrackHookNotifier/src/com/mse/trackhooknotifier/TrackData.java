package com.mse.trackhooknotifier;

import com.solipsys.geo.coordinate.Latitude;
import com.solipsys.geo.coordinate.Longitude;
import com.solipsys.tdf.test.client.TestTrackNumber;
import com.solipsys.tdf.track.Category;
import com.solipsys.tdf.track.Identity;
import com.solipsys.tdf.track.Platform;
import com.solipsys.tdf.track.TrackDatabase;
import com.solipsys.tdf.track.number.TrackNumberList;
import com.solipsys.tdf.track.report.TrackReport;
import com.solipsys.unit.Degrees;
import com.solipsys.unit.Kilofeet;
import com.solipsys.unit.MilesPerHour;
import com.solipsys.view.Representation;
import com.solipsys.view.Selectable;
import com.solipsys.view.event.ViewSelectEvent;
import com.solipsys.view.event.ViewSelectListener;

import com.solipsys.tdf.track.Track;
import com.solipsys.tdf.track.report.TrackReport;

public class TrackData {

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
    public TrackData(String tn, TrackReport tr) {
	try {

	    this.trackNumber = tn;
	    this.identity = Identity.getIdentity(tr.getIdentity(), false).toString();
	    this.category = Category.convertValue(tr.getCategory()).getName();
	    this.platform = Platform.getName(tr.getCategory(), tr.getPlatform());
	    this.latitude = Latitude.getLatitudeString(tr.getLatitude(), new Degrees());
	    this.longitude = Longitude.getLongitudeString(tr.getLongitude(), new Degrees());
	    this.speed = "" + tr.getSpeed(new MilesPerHour()) + " mph";
	    this.altitude = "" + tr.getAltitude(new Kilofeet()) + " kft";
	    this.course = "" + tr.getCourse(new Degrees()) + " degrees";
	}
	catch(Exception e ) 
	{
	    System.out.println("Make the damn code compile!");
	}
 		
    }

    public TrackData(String tn, String id, String cat, String plat, String lat, String lon, String spd,  String alt, String crs) {
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
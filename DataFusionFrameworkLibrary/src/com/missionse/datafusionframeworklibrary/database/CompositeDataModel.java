package com.missionse.datafusionframeworklibrary.database;

public class CompositeDataModel {
	/*
	 * Data held by the composite are stored as instances of their wrapper
	 * classes so that they have the ability to be null, letting the rest of the
	 * program know that the composite has not yet sent into this program info
	 * on that part of the sensor.
	 */

	// Sensor Info

	// The unique identification string for this composite.
	private String uniqueId;
	//
	private String sourceTypeKey;
	// The altitude source of the composite track.
	private Double compositeAlt;
	/*
	 * The error value of this composite's ability to sense things in variables
	 * X, Y and Z. X: The composite's accuracy in detecting the target's
	 * latitude and speed X. Y: The composite's accuracy in detecting the
	 * target's longitude and speed Y. Z: The composite's accuracy in detecting
	 * the target's altitude and speed Z.
	 */
	private Double errorX;
	private Double errorY;
	private Double errorZ;
	// The composite's update hertz value.
	private Double updateHertz;
	private Double altitude;
	// Data Feed

	// The x position of the target this sensor is sensing.
	private Double positionX;

	// The y position of the target this sensor is sensing.
	private Double positionY;
	// The z position of the target this sensor is sensing.
	private Double positionZ;
	// The speed of the target along the latitude plane.
	private Double speedX;
	// The speed of the target along the longitude plane.
	private Double speedY;
	// The speed of the target along the altitude plane.
	private Double speedZ;
	// How threatening the target is.
	private Integer threatLevel;
	// The track type of the target: Air, Surface, Subsurface.
	private String trackType;
	// The track platform of the target, depending on the track type.
	private String trackPlatform;
	// The track category of the target, depending on the track category.
	private String trackCategory;

	// For Database Purposes.
	public CompositeDataModel(String uniqueId, String sourceTypeKey,
			String trackPlatform, String trackCategory, int threatLevel,
			double speedX, double speedY, double compositeAlt, double errorX,
			double errorY, double updateHertz, double altitude, double positionX,
			double positionY, double errorZ, double positionZ, double speedZ) {

		this.uniqueId = uniqueId;
		this.sourceTypeKey = sourceTypeKey;
		this.trackPlatform = trackPlatform;
		this.trackCategory = trackCategory;
		this.threatLevel = threatLevel;
		this.speedX = speedX;
		this.speedY = speedY;
		this.compositeAlt = compositeAlt;
		this.errorX = errorX;
		this.errorY = errorY;
		this.updateHertz = updateHertz;
		this.altitude = altitude;
		this.positionX = positionX;
		this.positionY = positionY;
		this.errorZ = errorZ;
		this.positionZ = positionZ;
		this.speedZ = speedZ;

	}

	// Empty constructor.
	public CompositeDataModel() {
		uniqueId = null;
		sourceTypeKey = null;
		compositeAlt = null;

		errorX = null;
		errorY = null;
		errorZ = null;

		updateHertz = null;
		altitude = null;
		positionX = null;
		positionY = null;
		positionZ = null;

		speedX = null;
		speedY = null;
		speedZ = null;

		threatLevel = null;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
	}

	// UniqueID constructor.
	public CompositeDataModel(String id) {
		uniqueId = id;
		sourceTypeKey = null;
		compositeAlt = null;

		errorX = null;
		errorY = null;
		errorZ = null;

		updateHertz = null;
		altitude = null;
		positionX = null;
		positionY = null;
		positionZ = null;

		speedX = null;
		speedY = null;
		speedZ = null;

		threatLevel = null;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
	}

	// Various mutator and accessor methods for all variables in composite.

	// Sensor Info
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String id) {
		uniqueId = id;
	}

	public String getSourceTypeKey() {
		return sourceTypeKey;
	}

	public void setSourceTypeKey(String sourceTypeKey) {
		this.sourceTypeKey = sourceTypeKey;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getCompositeAltitude() {
		return compositeAlt;
	}

	public void setCompositeAltitude(Double a) {
		compositeAlt = a;
	}

	public Double getErrorX() {
		return errorX;
	}

	public void setErrorX(Double e) {
		errorX = e;
	}

	public Double getErrorY() {
		return errorY;
	}

	public void setErrorZ(Double e) {
		errorZ = e;
	}

	public Double getErrorZ() {
		return errorZ;
	}

	public void setErrorY(Double e) {
		errorY = e;
	}

	public Double getUpdateHertz() {
		return updateHertz;
	}

	public void setUpdateHertz(Double u) {
		updateHertz = u;
	}

	public Double getPositionX() {
		return positionX;
	}

	public void setPositionX(Double p) {
		positionX = p;
	}

	public Double getPositionY() {
		return positionY;
	}

	public void setPositionY(Double p) {
		positionY = p;
	}

	public Double getPositionZ() {
		return positionZ;
	}

	public void setPositionZ(Double p) {
		positionZ = p;
	}

	public Double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(Double p) {
		speedX = p;
	}

	public Double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(Double p) {
		speedY = p;
	}

	public Double getSpeedZ() {
		return speedZ;
	}

	public void setSpeedZ(Double p) {
		speedZ = p;
	}

	public Integer getThreatLevel() {
		return threatLevel;
	}

	public void setThreatLevel(Integer t) {
		threatLevel = t;
	}

	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String t) {
		trackType = t;
	}

	public String getTrackPlatform() {
		return trackPlatform;
	}

	public void setTrackPlatform(String t) {
		trackPlatform = t;
	}

	public String getTrackCategory() {
		return trackCategory;
	}

	public void setTrackCategory(String t) {
		trackCategory = t;
	}

	/**
	 * Given the parsed data, this method updates this composite's data with
	 * information that exists in the parsed data.
	 * 
	 * @param data
	 *            : The parsed data.
	 */
	public void update(String[] data) {

		// Index starts at 1 and goes until all variables have been accounted
		// for.
		int index = 1;

		// For each item, it checks to see if valid data for it exists and
		// assigns it.

		/*
		 * A variable is used instead of hard coded index numbers so that if new
		 * variables need to be added to composite, they can be added into this
		 * method without needing to update all subsequent index numbers.
		 */
		index++;

		try {
			compositeAlt = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			errorX = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			errorY = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			errorZ = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			updateHertz = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			positionX = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			positionY = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			positionZ = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			speedX = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			speedY = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			speedZ = new Double(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		try {
			threatLevel = new Integer(data[index]);
		} catch (NumberFormatException e) {
		}

		index++;

		if (data[index].compareTo("") != 0) {
			trackType = new String(data[index]);
		}

		index++;

		if (data[index].compareTo("") != 0) {
			trackPlatform = new String(data[index]);
		}

		index++;

		if (data[index].compareTo("") != 0) {
			trackCategory = new String(data[index]);
		}

	}

	// This method makes a clone of this composite, overwriting the default Java
	// clone method.
	public CompositeDataModel clone() {
		CompositeDataModel compositeClone = new CompositeDataModel(uniqueId);

		if (compositeAlt != null) {
			compositeClone.setCompositeAltitude(new Double(compositeAlt));
		}
		if (errorX != null) {
			compositeClone.setErrorX(new Double(errorX));
		}
		if (errorY != null) {
			compositeClone.setErrorY(new Double(errorY));
		}
		if (errorZ != null) {
			compositeClone.setErrorZ(new Double(errorZ));
		}
		if (updateHertz != null) {
			compositeClone.setUpdateHertz(new Double(updateHertz));
		}
		if (positionX != null) {
			compositeClone.setPositionX(new Double(positionX));
		}
		if (positionY != null) {
			compositeClone.setPositionY(new Double(positionY));
		}
		if (positionZ != null) {
			compositeClone.setPositionZ(new Double(positionZ));
		}
		if (speedX != null) {
			compositeClone.setSpeedX(new Double(speedX));
		}
		if (speedY != null) {
			compositeClone.setSpeedY(new Double(speedY));
		}
		if (speedZ != null) {
			compositeClone.setSpeedZ(new Double(speedZ));
		}
		if (threatLevel != null) {
			compositeClone.setThreatLevel(new Integer(threatLevel));
		}
		if (trackType != null) {
			compositeClone.setTrackType(new String(trackType));
		}
		if (trackPlatform != null) {
			compositeClone.setTrackPlatform(new String(trackPlatform));
		}
		if (trackCategory != null) {
			compositeClone.setTrackCategory(new String(trackCategory));
		}

		return compositeClone;
	}

	// For testing and driver purposes: not actually used in the program.
	public String toString() {
		String toReturn = uniqueId + ",";

		if (compositeAlt != null) {
			toReturn = toReturn + compositeAlt;
		}
		toReturn = toReturn + ",";
		if (errorX != null) {
			toReturn = toReturn + errorX;
		}
		toReturn = toReturn + ",";
		if (errorY != null) {
			toReturn = toReturn + errorY;
		}
		toReturn = toReturn + ",";
		if (errorZ != null) {
			toReturn = toReturn + errorZ;
		}
		toReturn = toReturn + ",";
		if (updateHertz != null) {
			toReturn = toReturn + updateHertz;
		}
		toReturn = toReturn + ",";
		if (positionX != null) {
			toReturn = toReturn + positionX;
		}
		toReturn = toReturn + ",";
		if (positionY != null) {
			toReturn = toReturn + positionY;
		}
		toReturn = toReturn + ",";
		if (positionZ != null) {
			toReturn = toReturn + positionZ;
		}
		toReturn = toReturn + ",";
		if (speedX != null) {
			toReturn = toReturn + speedX;
		}
		toReturn = toReturn + ",";
		if (speedY != null) {
			toReturn = toReturn + speedY;
		}
		toReturn = toReturn + ",";
		if (speedZ != null) {
			toReturn = toReturn + speedZ;
		}
		toReturn = toReturn + ",";
		if (threatLevel != null) {
			toReturn = toReturn + threatLevel;
		}
		toReturn = toReturn + ",";
		if (trackType != null) {
			toReturn = toReturn + trackType;
		}
		toReturn = toReturn + ",";
		if (trackPlatform != null) {
			toReturn = toReturn + trackPlatform;
		}
		toReturn = toReturn + ",";
		if (trackCategory != null) {
			toReturn = toReturn + trackCategory;
		}

		return toReturn;
	}

}

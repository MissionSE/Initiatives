package com.missionse.datafusionframeworklibrary.database;

public class SourceDataModel {

	/*
	 * Data held by the source are stored as instances of their wrapper classes
	 * so that they have the ability to be null, letting the rest of the program
	 * know that the source has not yet sent into this program info on that part
	 * of the sensor.
	 */

	// Sensor Info

	// The unique identification string for this source.
	private String uniqueId;

	private String sourceTrackType;

	// The altitude position of the sensor.
	private Double sourceAlt;
	/*
	 * The error value of this source's ability to sense things in variables X,
	 * Y and Z. X: The source's accuracy in detecting the target's latitude and
	 * speed X. Y: The source's accuracy in detecting the target's longitude and
	 * speed Y. Z: The source's accuracy in detecting the target's altitude and
	 * speed Z.
	 */
	private Double errorX;
	private Double errorY;
	private Double errorZ;
	// The source's update hertz value.
	private Double updateHertz;

	private Double altitude;
	// Data Feed

	// The latitude position of the target this sensor is sensing.
	private Double positionX;
	// The longitude position of the target this sensor is sensing.
	private Double positionY;
	// The altitude position of the target this sensor is sensing.
	private Double positionZ;
	// The speed of the target along the latitude plane.
	private Double speedX;
	// The speed of the target along the longitude plane.
	private Double speedY;
	// The speed of the target along the altitude plane.
	private Double speedZ;
	// The time of report.
	private Double time;
	// How threatening the target is.
	private Integer threatLevel;
	// The track type of the target.
	private String trackType;
	// The track platform of the target, depending on the track type.
	private String trackPlatform;
	// The track category of the target: Air, Surface, Subsurface.
	private String trackCategory;

	// For Database Purposes.
	public SourceDataModel(String uniqueId, String sourceTrackType,
			String trackPlatform, String trackCategory, int threatLevel,
			double speedX, double speedY, double sourceAlt, double errorX,
			double errorY, double updateHertz, double altitude,
			double positionX, double positionY, double errorZ,
			double positionZ, double speedZ, double time) {

		this.uniqueId = uniqueId;
		this.sourceTrackType = sourceTrackType;
		this.trackPlatform = trackPlatform;
		this.trackCategory = trackCategory;
		this.threatLevel = threatLevel;
		this.speedX = speedX;
		this.speedY = speedY;
		this.sourceAlt = sourceAlt;
		this.errorX = errorX;
		this.errorY = errorY;
		this.updateHertz = updateHertz;
		this.altitude = altitude;
		this.positionX = positionX;
		this.positionY = positionY;
		this.errorZ = errorZ;
		this.positionZ = positionZ;
		this.speedZ = speedZ;
		this.time = time;

	}

	// Empty constructor.
	public SourceDataModel() {
		uniqueId = null;
		sourceTrackType = null;
		sourceAlt = 0.0;

		errorX = 0.0;
		errorY = 0.0;
		errorZ = 0.0;

		updateHertz = 0.0;
		altitude = 0.0;
		positionX = 0.0;
		positionY = 0.0;
		positionZ = 0.0;

		speedX = 0.0;
		speedY = 0.0;
		speedZ = 0.0;
		
		time = 0.0;

		threatLevel = 0;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
	}

	// UniqueID constructor.
	public SourceDataModel(String id) {
		uniqueId = id;
		sourceTrackType = null;
		sourceAlt = 0.0;

		errorX = 0.0;
		errorY = 0.0;
		errorZ = 0.0;

		updateHertz = 0.0;
		altitude = 0.0;
		positionX = 0.0;
		positionY = 0.0;
		positionZ = 0.0;

		speedX = 0.0;
		speedY = 0.0;
		speedZ = 0.0;
		
		time = 0.0;

		threatLevel = 0;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
	}

	// Various mutator and accessor methods for all variables in Source.

	public String getSourceTrackType() {
		return sourceTrackType;
	}

	public void setSourceTrackType(String sourceTrackType) {
		this.sourceTrackType = sourceTrackType;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	// Sensor Info
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String id) {
		uniqueId = id;
	}

	public Double getSourceAltitude() {
		return sourceAlt;
	}

	public void setSourceAltitude(Double a) {
		sourceAlt = a;
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

	public Double getTime() {
		return time;
	}

	public void setTime(Double p) {
		time = p;
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
	 * Given the parsed data, this method updates this source's data with
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
		if (data[index].compareTo("") != 0) {
			sourceTrackType = new String(data[index]);
		}

		/*
		 * A variable is used instead of hard coded index numbers so that if new
		 * variables need to be added to source, they can be added into this
		 * method without needing to update all subsequent index numbers.
		 */
		index++;

		try {
			sourceAlt = new Double(data[index]);
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
			altitude = new Double(data[index]);
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
			time = new Double(data[index]);
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

	// This method makes a clone of this Source, overwriting the default Java
	// clone method.
	public SourceDataModel clone() {
		SourceDataModel sourceClone = new SourceDataModel(uniqueId);

		if (sourceTrackType != null) {
			sourceClone.setSourceTrackType(new String(sourceTrackType));
		}
		if (sourceAlt != null) {
			sourceClone.setSourceAltitude(new Double(sourceAlt));
		}
		if (errorX != null) {
			sourceClone.setErrorX(new Double(errorX));
		}
		if (errorY != null) {
			sourceClone.setErrorY(new Double(errorY));
		}
		if (errorZ != null) {
			sourceClone.setErrorZ(new Double(errorZ));
		}
		if (updateHertz != null) {
			sourceClone.setUpdateHertz(new Double(updateHertz));
		}
		if (altitude != null) {
			sourceClone.setAltitude(new Double(altitude));
		}
		if (positionX != null) {
			sourceClone.setPositionX(new Double(positionX));
		}
		if (positionY != null) {
			sourceClone.setPositionY(new Double(positionY));
		}
		if (positionZ != null) {
			sourceClone.setPositionZ(new Double(positionZ));
		}
		if (speedX != null) {
			sourceClone.setSpeedX(new Double(speedX));
		}
		if (speedY != null) {
			sourceClone.setSpeedY(new Double(speedY));
		}
		if (speedZ != null) {
			sourceClone.setSpeedZ(new Double(speedZ));
		}
		if (time != null) {
			sourceClone.setTime(new Double(time));
		}
		if (threatLevel != null) {
			sourceClone.setThreatLevel(new Integer(threatLevel));
		}
		if (trackType != null) {
			sourceClone.setTrackType(new String(trackType));
		}
		if (trackPlatform != null) {
			sourceClone.setTrackPlatform(new String(trackPlatform));
		}
		if (trackCategory != null) {
			sourceClone.setTrackCategory(new String(trackCategory));
		}

		return sourceClone;
	}

	// For testing and driver purposes: not actually used in the program.
	public String toString() {
		String toReturn = uniqueId + ",";

		if (sourceTrackType != null) {
			toReturn = toReturn + sourceTrackType;
		}
		toReturn = toReturn + ",";
		if (sourceAlt != null) {
			toReturn = toReturn + sourceAlt;
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
		if (altitude != null) {
			toReturn = toReturn + altitude;
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
		if (time != null) {
			toReturn = toReturn + time;
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

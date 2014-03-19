package com.missionse.datafusionframeworklibrary.databaselibrary;

public class CompositeDataModel {
	/*
	 * Data held by the composite are stored as instances of their wrapper classes so that they have the
	 * ability to be null, letting the rest of the program know that the composite has not yet sent into
	 * this program info on that part of the sensor.
	 */

	//Sensor Info

	//The unique identification string for this composite.
	private String uniqueId;
	//
	private Integer sourceTypeKey;
	//The latitude position of the sensor.
	private Double compositeLat;
	//The longitude position of the sensor.
	private Double compositeLong;
	//The altitude position of the sensor.
	private Double compositeAlt;
	/*
	 * The error value of this composite's ability to sense things in variables X, Y and Z.
	 * X: The composite's accuracy in detecting the target's latitude and speed X.
	 * Y: The composite's accuracy in detecting the target's longitude and speed Y.
	 * Z: The composite's accuracy in detecting the target's altitude and speed Z.
	 *
	 */
	private Double errorX;
	private Double errorY;
	private Double errorZ;
	//The composite's update hertz value.
	private Double updateHertz;
	private Double depthZ;
	//Data Feed

	//The latitude position of the target this sensor is sensing.
	private Double positionLat;

	//The longitude position of the target this sensor is sensing.
	private Double positionLong;
	//The altitude position of the target this sensor is sensing.
	private Double positionAlt;
	//The speed of the target along the latitude plane.
	private Double speedX;
	//The speed of the target along the longitude plane.
	private Double speedY;
	//The speed of the target along the altitude plane.
	private Double speedZ;
	//How threatening the target is.
	private Integer threatLevel;
	//The track type of the target: Air, Surface, Subsurface.
	private String trackType;
	//The track platform of the target, depending on the track type.
	private String trackPlatform;
	//The track category of the target, depending on the track category.
	private String trackCategory;
	//The fuel that the target has the remaining.
	private Double fuel;

	//For Database Purposes.
	public CompositeDataModel(String uniqueId, int sourceTypeKey, String trackPlatform,
			String trackCategory, int threatLevel, double speedX, double speedY, double compositeLat,
			double compositeLong, double compositeAlt, double fuel, double errorX,
			double errorY, double updateHertz, double depthZ, double positionLat, double positionLong,
			double errorZ, double positionAlt, double speedZ) {



		this.uniqueId = uniqueId;
		this.sourceTypeKey = sourceTypeKey;
		this.trackPlatform = trackPlatform;
		this.trackCategory = trackCategory;
		this.threatLevel = threatLevel;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
		this.compositeLat = compositeLat;
		this.compositeLong = compositeLong;
		this.compositeAlt = compositeAlt;
		this.fuel = fuel;
		this.errorX = errorX;
		this.errorY = errorY;
		this.updateHertz = updateHertz;
		this.depthZ = depthZ;
		this.positionLat = positionLat;
		this.positionLong = positionLong;
		this.errorZ = errorZ;
		this.positionAlt = positionAlt;
		this.speedZ = speedZ;


	}

	//Empty constructor.
	public CompositeDataModel()
	{
		uniqueId = null;
		sourceTypeKey = null;
		compositeLat = null;
		compositeLong = null;
		compositeAlt = null;

		errorX = null;
		errorY = null;
		errorZ = null;

		updateHertz = null;
		depthZ = null;
		positionLat = null;
		positionLong = null;
		positionAlt = null;

		speedX = null;
		speedY = null;
		speedZ = null;

		threatLevel = null;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
		fuel = null;
	}

	//UniqueID constructor.
	public CompositeDataModel(String id) {
		uniqueId = id;
		sourceTypeKey = null;
		compositeLat = null;
		compositeLong = null;
		compositeAlt = null;

		errorX = null;
		errorY = null;
		errorZ = null;

		updateHertz = null;
		depthZ = null;
		positionLat = null;
		positionLong = null;
		positionAlt = null;

		speedX = null;
		speedY = null;
		speedZ = null;

		threatLevel = null;
		trackType = null;
		trackPlatform = null;
		trackCategory = null;
		fuel = null;
	}

	//Various mutator and accessor methods for all variables in composite.

	//Sensor Info
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String id) {
		uniqueId = id;
	}

	public Integer getSourceTypeKey() {
		return sourceTypeKey;
	}

	public void setSourceTypeKey(Integer sourceTypeKey) {
		this.sourceTypeKey = sourceTypeKey;
	}

	public Double getDepthZ() {
		return depthZ;
	}

	public void setDepthZ(Double depthZ) {
		this.depthZ = depthZ;
	}

	public Double getCompositeLatitude() {
		return compositeLat;
	}

	public void setCompositeLatitude(Double l) {
		compositeLat = l;
	}

	public Double getCompositeLongitude() {
		return compositeLong;
	}

	public void setCompositeLongitude(Double l) {
		compositeLong = l;
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

	public Double getPositionLatitude() {
		return positionLat;
	}

	public void setPositionLatitude(Double p) {
		positionLat = p;
	}

	public Double getPositionLongitude() {
		return positionLong;
	}

	public void setPositionLongitude(Double p) {
		positionLong = p;
	}

	public Double getPositionAltitude() {
		return positionAlt;
	}

	public void setPositionAltitude(Double p) {
		positionAlt = p;
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

	public Double getFuel() {
		return fuel;
	}

	public void setFuel(Double f) {
		fuel = f;
	}

	/**
	 * Given the parsed data, this method updates this composite's data with information that
	 * exists in the parsed data.
	 *
	 * @param data: The parsed data.
	 */
	 public void update(String[] data)
	{

		//Index starts at 1 and goes until all variables have been accounted for.
		int index = 1;

				//For each item, it checks to see if valid data for it exists and assigns it.
				try {
					compositeLat = new Double(data[index]);
				} catch (NumberFormatException e) {
				}

				/*
				 * A variable is used instead of hard coded index numbers so that if new variables need to be
				 * added to composite, they can be added into this method without needing to update all
				 * subsequent index numbers.
				 */
				index++;

				try {
					compositeLong = new Double(data[index]);
				} catch (NumberFormatException e) {
				}

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
					positionLat = new Double(data[index]);
				} catch (NumberFormatException e) {
				}

				index++;

				try {
					positionLong = new Double(data[index]);
				} catch (NumberFormatException e) {
				}

				index++;

				try {
					positionAlt = new Double(data[index]);
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

				index++;

				try {
					fuel = new Double(data[index]);
				} catch (NumberFormatException e) {
				}
	}

	 //This method makes a clone of this composite, overwriting the default Java clone method.
	 public CompositeDataModel clone()
	 {
		 CompositeDataModel compositeClone = new CompositeDataModel(uniqueId);

		 if(compositeLat != null)
		 {
			 compositeClone.setCompositeLatitude(new Double(compositeLat));
		 }
		 if(compositeLong != null)
		 {
			 compositeClone.setCompositeLongitude(new Double(compositeLong));
		 }
		 if(compositeAlt != null)
		 {
			 compositeClone.setCompositeAltitude(new Double(compositeAlt));
		 }
		 if(errorX != null)
		 {
			 compositeClone.setErrorX(new Double(errorX));
		 }
		 if(errorY != null)
		 {
			 compositeClone.setErrorY(new Double(errorY));
		 }
		 if(errorZ != null)
		 {
			 compositeClone.setErrorZ(new Double(errorZ));
		 }
		 if(updateHertz != null)
		 {
			 compositeClone.setUpdateHertz(new Double(updateHertz));
		 }
		 if(positionLat != null)
		 {
			 compositeClone.setPositionLatitude(new Double(positionLat));
		 }
		 if(positionLong != null)
		 {
			 compositeClone.setPositionLongitude(new Double(positionLong));
		 }
		 if(positionAlt != null)
		 {
			 compositeClone.setPositionAltitude(new Double(positionAlt));
		 }
		 if(speedX != null)
		 {
			 compositeClone.setSpeedX(new Double(speedX));
		 }
		 if(speedY != null)
		 {
			 compositeClone.setSpeedY(new Double(speedY));
		 }
		 if(speedZ != null)
		 {
			 compositeClone.setSpeedZ(new Double(speedZ));
		 }
		 if(threatLevel != null)
		 {
			 compositeClone.setThreatLevel(new Integer(threatLevel));
		 }
		 if(trackType != null)
		 {
			 compositeClone.setTrackType(new String(trackType));
		 }
		 if(trackPlatform != null)
		 {
			 compositeClone.setTrackPlatform(new String(trackPlatform));
		 }
		 if(trackCategory != null)
		 {
			 compositeClone.setTrackCategory(new String(trackCategory));
		 }
		 if(fuel != null)
		 {
			 compositeClone.setFuel(new Double(fuel));
		 }

		 return compositeClone;
	 }

	 //For testing and driver purposes: not actually used in the program.
	 public String toString()
	 {
		 String toReturn = uniqueId + ",";

		 if(compositeLat != null)
		 {
			 toReturn = toReturn + compositeLat;
		 }
		 toReturn = toReturn + ",";
		 if(compositeLong != null)
		 {
			 toReturn = toReturn + compositeLong;
		 }
		 toReturn = toReturn + ",";
		 if(compositeAlt != null)
		 {
			 toReturn = toReturn + compositeAlt;
		 }
		 toReturn = toReturn + ",";
		 if(errorX != null)
		 {
			 toReturn = toReturn + errorX;
		 }
		 toReturn = toReturn + ",";
		 if(errorY != null)
		 {
			 toReturn = toReturn + errorY;
		 }
		 toReturn = toReturn + ",";
		 if(errorZ != null)
		 {
			 toReturn = toReturn + errorZ;
		 }
		 toReturn = toReturn + ",";
		 if(updateHertz != null)
		 {
			 toReturn = toReturn + updateHertz;
		 }
		 toReturn = toReturn + ",";
		 if(positionLat != null)
		 {
			 toReturn = toReturn + positionLat;
		 }
		 toReturn = toReturn + ",";
		 if(positionLong != null)
		 {
			 toReturn = toReturn + positionLong;
		 }
		 toReturn = toReturn + ",";
		 if(positionAlt != null)
		 {
			 toReturn = toReturn + positionAlt;
		 }
		 toReturn = toReturn + ",";
		 if(speedX != null)
		 {
			 toReturn = toReturn + speedX;
		 }
		 toReturn = toReturn + ",";
		 if(speedY != null)
		 {
			 toReturn = toReturn + speedY;
		 }
		 toReturn = toReturn + ",";
		 if(speedZ != null)
		 {
			 toReturn = toReturn + speedZ;
		 }
		 toReturn = toReturn + ",";
		 if(threatLevel != null)
		 {
			 toReturn = toReturn + threatLevel;
		 }
		 toReturn = toReturn + ",";
		 if(trackType != null)
		 {
			 toReturn = toReturn + trackType;
		 }
		 toReturn = toReturn + ",";
		 if(trackPlatform != null)
		 {
			 toReturn = toReturn + trackPlatform;
		 }
		 toReturn = toReturn + ",";
		 if(trackCategory != null)
		 {
			 toReturn = toReturn + trackCategory;
		 }
		 toReturn = toReturn + ",";
		 if(fuel != null)
		 {
			 toReturn = toReturn + fuel;
		 }

		 return toReturn;
	 }

}

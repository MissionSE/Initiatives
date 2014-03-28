package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import java.sql.SQLException;

import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class PacketReciever is the main input class for this program. It takes in packets of data
 * in the form of strings through the method recievePacket, creates/updates a Source object
 * with the given data, calls the CommonFieldParser to correlate it with other Sources and sends
 * off the data to other parts of the program.
 *
 * This class was designed to handle any number of sources and deal with any and all irregular
 * or erroneous input.
 *
 * @author Matt Asfar
 */
public class PacketReceiver {
	// Reference to package classes
	DataFusion df;

	// Reference to the Database
	SourceDataAccessor sdb;
	CompositeDataAccessor cdb;

	// The number of variables that this program uses to represent a Source.
	private int numSourceVariables;

	/*
	 * Constructor for PacketReciever
	 * 
	 * 1 - Creates a new instance of Database and CommonFieldParser for use with
	 * this module. 2 - Sets the ObjectRefinementModule to null, as it will be
	 * created later. 3 - Creates a new ArrayList for storage of Sources.
	 */
	public PacketReceiver(SourceDataAccessor sourceDataAccess,
			CompositeDataAccessor compositeDataAccess) {
		// 1
		sdb = sourceDataAccess;
		cdb = compositeDataAccess;
		// 2
		df = new DataFusion(sourceDataAccess, compositeDataAccess);

		/*
		 * This line of code sets numSourceVariables to however many class
		 * variables make up the Source class. This used to be a static final
		 * variable in class Source, but now is set dynamically at runtime so
		 * that any alterations to class Source will be automatically reflected
		 * instead of counting the variables, reassigning the static field, etc.
		 */
		numSourceVariables = SourceDataModel.class.getDeclaredFields().length;
	}

	// For database use.
	public void end() throws SQLException {
		sdb.shutdown();
		cdb.shutdown();
	}

	/*
	 * This is the method where the packet, in the form of a String, is
	 * received. The String received holds values given from the source
	 * separated by commas.
	 * 
	 * Given Strings must be in the format: "Sensor's unique identification
	 * string, Latitude position of the sensor, Longitude position of the
	 * sensor, Altitude position of the sensor, Error X value of the sensor,
	 * Error Y value of the sensor, Error Z value of the sensor, The update
	 * hertz of the sensor, Latitude position of what the sensor is sensing,
	 * Longitude position of what the sensor is sensing, Altitude position of
	 * what the sensor is sensing, Speed X of what the sensor is sensing, Speed
	 * Y of what the sensor is sensing, Speed Z of what the sensor is sensing,
	 * The threat level of the what the sensor is sensing, The string track type
	 * of what the sensor is sensing: Air, Surface or Subsurface, The string
	 * track platform of what the sensor is sensing, depending on the track
	 * type, The string track category of what the sensor is sensing, depending
	 * on the track type, The level of fuel remaining for what the sensor is
	 * sensing"
	 */
	public void recievePacket(String data) {

		// Parse the data into an array of Strings, deliminated by commas.
		String[] parsedData = data.split(",", -1);

		System.out.println("***recievePacket data: " + data);

		/*
		 * If the amount of given data does not match up with the amount of data
		 * this program uses to represent sources or if the data does not
		 * contain an unique identifier, something has gone wrong and the data
		 * is ignored.
		 */
		// System.out.println("receivePacket parsedData.length: "+parsedData.length);
		// System.out.println("receivePacket numSourceVariables: "+numSourceVariables);

		if (parsedData.length != numSourceVariables
				|| parsedData[0].compareTo("") == 0) {
			return;
		}

		df.dataFusion(parsedData);
	}
}

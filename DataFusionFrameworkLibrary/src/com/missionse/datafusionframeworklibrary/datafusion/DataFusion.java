package com.missionse.datafusionframeworklibrary.datafusion;

import java.sql.SQLException;
import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.dataassociation.DataAssociation;
import com.missionse.datafusionframeworklibrary.database.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

/*
 * Class DataFusion is the main input class for this program. It takes in packets of data
 * in the form of strings through the method recievePacket, creates/updates a Source object
 * with the given data, calls the CommonFieldParser to correlate it with other Sources and sends
 * off the data to other parts of the program.
 */
public class DataFusion implements DataFusionProvider {
	// Reference to package classes
	DataAssociation da;
	CompositeTrackIdAssignment n;
	CorrelateSources cs;

	// Reference to the database
	SourceDataAccessor sdb;
	CompositeDataAccessor cdb;

	/*
	 * Constructor for DataFusion
	 * 
	 * 1 - Creates a new instance CommonFieldParser for use with this module. 2
	 * - Sets the ObjectRefinementModule to null, as it will be created later. 3
	 * - Creates a new ArrayList for storage of Sources.
	 */
	public DataFusion(SourceDataAccessor sourceDataAccess,
			CompositeDataAccessor compositeDataAccess) {
		// 1
		this.sdb = sourceDataAccess;
		this.cdb = compositeDataAccess;
		// 2
		da = new DataAssociation(compositeDataAccess);
		n = new CompositeTrackIdAssignment();
		cs = new CorrelateSources(sourceDataAccess, compositeDataAccess);

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
	public void dataFusion(String[] parsedData) {

		// Storage for, and retrieval of, the Source this data represents.
		SourceDataModel toUpdate = searchExistingSources(parsedData[0]);

		/*
		 * If this program is not yet observing a Source with the given unique
		 * identification, a new one will be created.
		 */
		if (toUpdate == null) {
			toUpdate = createNewSource(parsedData[0]);
		}

		/*
		 * Whether the data represents an already existing source or a newly
		 * created one, update the source with.
		 */
		toUpdate.update(parsedData);
		System.out.println("dataFusion toUpdate: " + toUpdate);

		// Here the newly updated source is sent to be saved by the Database
		try {
			sdb.updateSourceBuilder(toUpdate.clone());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * This method will perform data association
		 */
		ArrayList<String> candidates = da.associateMeasurement(toUpdate);
		System.out.println("dataFusion candidates: " + candidates);

		/*
		 * If no candidates are returned, then this is the first reception of
		 * source data for this object. Need to create a unique composite id to
		 * store source data in composite db so that it can be candidate for
		 * future associations
		 */
		String compositeTrackKey;
		String c = "C";
		if (candidates.isEmpty())
			compositeTrackKey = c + Integer.toString(n.getNum());
		else
			// TODO perform candidate validation
			compositeTrackKey = candidates.get(0);

		System.out.println("dataFusion compositeTrackKey : "
				+ compositeTrackKey);

		/*
		 * Once this class has done everything it needs to do, this method
		 * allows it to send off data to other sections of the program.
		 */
		cs.correlateSources(toUpdate, compositeTrackKey);
	}

	/*
	 * This method searches through the sources that already exist, looking for
	 * one with the given unique ID. If no such source is found, it returns
	 * null.
	 */
	private SourceDataModel searchExistingSources(String id) {
		SourceDataModel source = null;
		try {
			source = sdb.querySourceBuilder(id, System.currentTimeMillis());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * for (SourceDataModel s : sources) { if (s.getUniqueId().compareTo(id)
		 * == 0) { return s; } }
		 */
		return source;
	}

	/*
	 * This method creates, returns and adds to the list of observed sources a
	 * new Source with the given identification string. A source created this
	 * way has nothing else set yet, effectively making it empty.
	 */
	private SourceDataModel createNewSource(String id) {
		SourceDataModel newSource = new SourceDataModel(id);
		System.out.println("dataFusion:createNewSource: " + newSource);
		return newSource;
	}
}

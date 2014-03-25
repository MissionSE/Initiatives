package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import java.sql.SQLException;
import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.dataassociationlibrary.DataAssociation;
import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class DataFusion is the main input class for this program. It takes in packets of data
 * in the form of strings through the method recievePacket, creates/updates a Source object
 * with the given data, calls the CommonFieldParser to correlate it with other Sources and sends
 * off the data to other parts of the program.
 */
public class DataFusion {
	// Reference to package classes
	DataAssociation da;
	Number n;
	CorrelateSources cs;
	PackSupportingData psd;

	// Reference to the database
	SourceDataAccessor sdb;
	CompositeDataAccessor cdb;

	// The list of currently observed Sources.
	private ArrayList<SourceDataModel> sources;

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
		n = new Number();
		cs = new CorrelateSources();
		psd = new PackSupportingData(sourceDataAccess);
		// 3
		sources = new ArrayList<SourceDataModel>();

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

		System.out.println("dataFusion parsedData[0]: " + parsedData[0]);

		// Storage for, and retrieval of, the Source this data represents.
		SourceDataModel toUpdate = searchExistingSources(parsedData[0]);
		System.out.println("dataFusion sources: " + sources);

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
//		try {
//			sdb.updateSourceBuilder(toUpdate.clone());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("dataFusion sources before association: " + sources);
		
		/*
		 * This method will perform data association
		 */
		ArrayList<String> candidates = da.associateMeasurement(toUpdate);
		System.out.println("dataFusion candidates: " + candidates);
		
		/* If no candidates are returned, then this is the first reception of
		 * source data for this object. Need to create a unique composite id to
		 * store source data in composite db so that it can be candidate for
		 * future associations
		 * */
		String uniqueId;
		if (candidates == null)
			uniqueId = "3CS"; // uniqueId = n.getNum;
		else
			// TODO perform candidate validation
			uniqueId = candidates.get(0);
		
		
		// TODO populate sources with all contibuting source data of the
		// composite track
		
		System.out.println("dataFusion sources : " +sources);
		System.out.println("dataFusion uniqueId: " +uniqueId);

		// Correlate all observed data into a correlated source.
		SourceDataModel correlated = cs.correlateSources(toUpdate, sources, uniqueId);

		System.out.println("dataFusion correlated: " + correlated);

		// Send off the newly updated source and the correlated one to the rest
		// of the program.
		sendUpdates(toUpdate, correlated);
	}

	/*
	 * This method searches through the sources that already exist, looking for
	 * one with the given unique ID. If no such source is found, it returns
	 * null.
	 */
	private SourceDataModel searchExistingSources(String id) {
		for (SourceDataModel s : sources) {
			if (s.getUniqueId().compareTo(id) == 0) {
				return s;
			}
		}

		return null;
	}

	/*
	 * This method creates, returns and adds to the list of observed sources a
	 * new Source with the given identification string. A source created this
	 * way has nothing else set yet, effectively making it empty.
	 */
	private SourceDataModel createNewSource(String id) {
		SourceDataModel newSource = new SourceDataModel(id);
		sources.add(newSource);
		System.out.println("dataFusion:createNewSource sources: " + sources);
		return newSource;
	}

	/*
	 * Once this class has done everything it needs to do, this method allows it
	 * to send off data to other sections of the program.
	 */
	private void sendUpdates(SourceDataModel toUpdate,
			SourceDataModel correlated) {
		// Here the newly updated source and the correlated source are sent to
		// be saved by the Database.
		try {
			System.out.println("dataFusion:sendUpdates toUpdate = " + toUpdate);
			sdb.updateSourceBuilder(toUpdate.clone());
			System.out.println("dataFusion:sendUpdates correlated = "
					+ correlated);
			sdb.updateSourceBuilder(correlated.clone());
		} catch (Exception e) {
		}
		System.out.println("dataFusion:sendUpdates correlated = " + correlated);
		System.out.println("dataFusion:sendUpdates sources = " + sources);
		psd.packSupportingData(toUpdate, correlated, sources);
	}

}

package com.missionse.datafusionframeworklibrary.datafusion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.missionse.datafusionframeworklibrary.database.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

public class CorrelateSources {
	// Reference to package classes
	CommonFieldParser cfp;
	PackSupportingData psd;

	// Reference to the database
	SourceDataAccessor sdb;
	CompositeDataAccessor cdb;

	// The list of currently observed Sources.
	private ArrayList<SourceDataModel> sources;

	// Correlate all observed data into a correlated source.
	private SourceDataModel correlated;

	public CorrelateSources(SourceDataAccessor sourceDataAccess,
			CompositeDataAccessor compositeDataAccess) {
		this.sdb = sourceDataAccess;
		this.cdb = compositeDataAccess;

		cfp = new CommonFieldParser();
		psd = new PackSupportingData(compositeDataAccess);
	}

	/**
	 * This method creates a new ArrayList, adds to it clones of the sources
	 * this class is observing and send off the list to be correlated by the
	 * CommonFieldParser. It will return the correlated source that is returned
	 * by the parser.
	 * 
	 * @return
	 */

	public void correlateSources(SourceDataModel toUpdate,
			String compositeTrackKey) {

		// list of currently observed source ids per composite track
		List<String> sourceId = new ArrayList<String>();

		// array of currently observed source data per composite track
		sources = new ArrayList<SourceDataModel>();

		// get list of all contributing sources for composite tracks
		try {
			sourceId = cdb.fetchSourcesForComposite(compositeTrackKey);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("correlateSources: sourceId " + sourceId);

		// populate sources array with new source data
		sources.add(toUpdate);

		// add all other contributing sources
		if (sourceId != null) {
			for (String item : sourceId) {
				if (item.equals(toUpdate.getUniqueId())) {
				} else
					try {
						sources.add(sdb.querySourceBuilder(item,
								System.currentTimeMillis()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

		System.out.println("correlateSources: sources " + sources);

		// Correlate all observed data into a correlated source.
		correlated = new SourceDataModel();

		if (sources.size() == 1) {
			correlated = sources.get(0).clone();
			correlated.setUniqueId(compositeTrackKey);
		} else
			correlated = cfp.correlateSources(sources, compositeTrackKey);

		System.out.println("correlateSources: correlated " + correlated);

		// Send off the newly updated source and the correlated one to the rest
		// of the program.
		sendUpdates(toUpdate);
	}

	/*
	 * Once this class has done everything it needs to do, this method allows it
	 * to send off data to other sections of the program.
	 */
	private void sendUpdates(SourceDataModel toUpdate) {
		// Here the correlated source is sent to be saved by the Database.
		String compositeTrackKey = correlated.getUniqueId();
		String sourceTrackKey = toUpdate.getUniqueId();

		try {
			cdb.updateCompositeSourceCrossReference(compositeTrackKey,
					sourceTrackKey);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cdb.updateCompositeBuilder(correlated);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		psd.packSupportingData(toUpdate, correlated, sources, compositeTrackKey);

	}

}
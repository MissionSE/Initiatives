package com.missionse.datafusionframeworklibrary.datafusionlibrary;

public interface DataFusionProvider {

	/**
	 * Given a data set of parsed data, this method will attempt to
	 * track fusion.
	 * @param parsedData
	 */
	public void dataFusion(String[] parsedData);
}

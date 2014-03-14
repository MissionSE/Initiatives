package com.missionse.datafusionframeworklibrary.datafusionlibrary;

public interface DataFusion {
	
	void associateMeasurement();
	void extrapolate();
	void update();
	void refineObject();
	void calculateCovariance();

}

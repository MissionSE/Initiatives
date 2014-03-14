package com.missionse.datafusionframeworklibrary;

public interface DataFusion {
	
	void associateMeasurement();
	void extrapolate();
	void update();
	void refineObject();
	void calculateCovariance();

}

package datacorrelation;

public interface DataFusion {
	
	void associateMeasurement();
	void extrapolate();
	void update();
	void refineObject();
	void calculateCovariance();

}

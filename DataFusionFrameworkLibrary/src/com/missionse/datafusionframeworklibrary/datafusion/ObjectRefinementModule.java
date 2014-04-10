package com.missionse.datafusionframeworklibrary.datafusion;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.missionse.datafusionframeworklibrary.database.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

/**
 * <p>
 * Object Refinement Module receives a new measurement source, and attempts to
 * apply it to the currently tracked state object. A Kalman filter is used to
 * approximate the target object, through multiple sensors. A Gating filter is
 * used to detect possible faulty measurements.
 * </p>
 * 
 * 
 * @author George Pierce
 * @date 12/2010
 */
public class ObjectRefinementModule {

	// ***************KALMAN FILTER
	// EQUATIONS****************************************
	// x = Ax + Bu + w meaning the state vector x evolves during one time
	// step by premultiplying by the "state transition
	// matrix" A. There is optionally (if nonzero) an input
	// vector u which affects the state linearly, and this
	// linear effect on the state is represented by
	// premultiplying by the "input matrix" B. There is also
	// gaussian process noise w.
	// z = Hx + v meaning the observation vector z is a linear function
	// of the state vector, and this linear relationship is
	// represented by premultiplication by "observation
	// matrix" H. There is also gaussian measurement
	// noise v.
	// where w ~ N(0,Q) meaning w is gaussian noise with covariance Q
	// v ~ N(0,R) meaning v is gaussian noise with covariance R
	//
	//
	// State_x corresponds to the object state vector, which has
	// position(x,y,z),
	// velocity(Vx, Vy,Vz), and acceleration(Ax,Ay,Az)
	//
	// These equations are from the laws of motion,
	// a state estimate consists of the following nine elements:
	//
	// X: X_0 + Vx* dt
	// Y: Y_0 + Vy*dt
	// Z: Z_0 + Vz*dt
	// Vx: Vx_0 + Ax*dt
	// Vy: Vy_0 + Ay*dt
	// Vz: Vz_0 + Az*dt
	// Ax: Vx_k - Vx_k-1/ TIME_DIFFERENCE_OF_SOURCES
	// Ay: Vy_k - Vy_k-1/ TIME_DIFFERENCE_OF_SOURCES
	// Az: Vz_k - Vz_k-1/ TIME_DIFFERENCE_OF_SOURCES
	//
	// The A matrix corresponds to state transition matrix
	// A= { {1, 0, 0, dt, 0, 0, 0, 0, 0}, X
	// {0, 1, 0, 0, dt,0, 0, 0, 0}, Y
	// {0, 0, 1, 0, 0, dt,0, 0, 0}, Z
	// {0, 0, 0, 1, 0, 0, dt,0, 0}, Dx
	// {0, 0, 0, 0, 1, 0, 0, dt,0}, Dy
	// {0, 0, 0, 0, 0, 1, 0, 0, dt}, Dz
	// {0, 0, 0, 0, 0, 0, 1, 0, 0}, Ax
	// {0, 0, 0, 0, 0, 0, 0, 1, 0}, Ay
	// {0, 0, 0, 0, 0, 0, 0, 0, 1}, Az
	// }
	//
	// *******************MATRICES/VECTORS**********************************
	//
	//
	// Accepted probability for incoming measurements to the Gating Filter
	private static double CONFIDENCE_PROBABILITY = .17;
	private Matrix state_x_pre; // state vector prediction estimate
	private Matrix state_x_post; // state vector correction estimate based on
	// measurements
	private Matrix measurement_matrix; // measurement Matrix ~H matrix
	private Matrix covariance_r; // Covariance for measurement noise R
	private Matrix kalmanGain;
	private Matrix state_transition; // state transition matrix
	private Matrix covariance_p_pre; // covariance of the priori state estimate
	private Matrix covariance_p_post; // covariance of posterio state estimate
	private Matrix covariance_q; // Covariance for process noise Q
	private double prevSpeedX; // Store previousX Speed for Acceleration
	// computation.
	private double prevSpeedY; // Store previousY Speed for Acceleration
	// computation.
	private double prevSpeedZ; // Store previousZ Speed for Acceleration
	// computation.
	// private double NUMBER_SOURCES = 3;
	private static final int STATE_ELEMENTS_SIZE = 9; // NUMBER OF state vector
	// properties
	CompositeDataAccessor db;
	// Analysis analysisModule;
	// that identify our object state
	// Time difference is equated from |s1.updateHertz-s2.updateHertz|
	private static double TIME_DIFFERENCE_OF_SOURCES;

	// **************************METHODS*******************************************
	public ObjectRefinementModule(SourceDataModel sourceDataModel,
			CompositeDataAccessor db2) {

		this.db = db2;
		// analysisModule = new Analysis(source, db, this);
		// Initialize the kalman filter
		// Time = 1/HZ
		TIME_DIFFERENCE_OF_SOURCES = (1 / sourceDataModel.getUpdateHertz());

		// Intialize all matrices/vectors

		// Initializes the prediction and correction estimates to first
		// measurment
		state_x_pre = assembleStateEst(sourceDataModel);

		state_x_post = state_x_pre;

		double[][] tr = {
				{ 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 0, TIME_DIFFERENCE_OF_SOURCES },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1 } };
		state_transition = new Matrix(tr);
		// Sets previousSpeeds for future Acceleration computation
		prevSpeedX = sourceDataModel.getSpeedX();
		prevSpeedY = sourceDataModel.getSpeedY();
		prevSpeedZ = sourceDataModel.getSpeedZ();

		measurement_matrix = Matrix.identity(STATE_ELEMENTS_SIZE,
				STATE_ELEMENTS_SIZE);
		covariance_q = new Matrix(STATE_ELEMENTS_SIZE, STATE_ELEMENTS_SIZE);
		covariance_p_pre = Matrix.identity(STATE_ELEMENTS_SIZE,
				STATE_ELEMENTS_SIZE);
		covariance_p_post = Matrix.identity(STATE_ELEMENTS_SIZE,
				STATE_ELEMENTS_SIZE);
		covariance_r = new Matrix(STATE_ELEMENTS_SIZE, STATE_ELEMENTS_SIZE);
		kalmanGain = new Matrix(STATE_ELEMENTS_SIZE, STATE_ELEMENTS_SIZE);

	}

	/**
	 * Refine Object takes a collection of source measurements and applies a
	 * kalman filter to them. An average of the summation of all sources is
	 * computed and the state vector post estimate is acquired through this
	 * calculation. An average between all state vector estimates for each
	 * source measurement
	 * 
	 * @param sources
	 *            :array of sources, where ASSUMED: Index[0] = Correlated source
	 *            object index[1..N]: Original Sources with error values
	 *            preserved for kalman filtering
	 */
	public void refineObject(SourceDataModel[] sourceDataModels) {

		// Step1: Compute the prediction phase based on Laws of physics
		computePredictionPhase();

		// Step2: For each source measurement, we compute a state vector
		// estimate
		// for it, then compute the average between all state measurement
		// Estimates.
		// To create a better estimate from our observed measurements.
		double[][] placeHolder = { { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 },
				{ 0 }, { 0 }, { 0 } };

		Matrix tempStateVector = new Matrix(placeHolder);
		SourceDataModel temp = sourceDataModels[0]; // Corresponds to correlated
		// Source

		// Check whether we only have one source to fuse, thus we only compute
		// Correction
		// for the single updated measurement
		if (sourceDataModels.length == 2) {
			temp.setErrorX(sourceDataModels[1].getErrorX());
			temp.setErrorY(sourceDataModels[1].getErrorY());
			temp.setErrorZ(sourceDataModels[1].getErrorZ());
			state_x_post = computeCorrectionPhase(temp);
		} else {
			for (int i = 1; i < sourceDataModels.length; i++) {
				// *NOTE* This resets the error for each individual measurement,
				// which allows the filter to fuse the correlated source with
				// error percentage for each source, allowing for a better
				// overall
				// measurement
				temp.setErrorX(sourceDataModels[i].getErrorX());
				temp.setErrorY(sourceDataModels[i].getErrorY());
				temp.setErrorZ(sourceDataModels[i].getErrorZ());
				tempStateVector.plusEquals(computeCorrectionPhase(temp));

			}

			// Now that each measurementSource has been Kalmanized and added
			// together,
			// Next Step is to compute average of them
			double[][] averageVector = tempStateVector.getArray();

			for (int row = 0; row < STATE_ELEMENTS_SIZE; row++) {
				averageVector[row][0] = averageVector[row][0]
						/ (sourceDataModels.length - 1);
			}
			// Now that we have the Average of kalmanized measurements, create a
			// new state_X_post based off of it.
			state_x_post = new Matrix(averageVector);
			// System.out.println("state_x_post= " + state_x_post);

		}
		// Assemble the new source from state vector
		SourceDataModel updatedSource = reconstructSource(sourceDataModels[0]);
		System.out.println("orm.refineObject: updatedSource " + updatedSource);

		// Update the prevSpeed for our Object for future acceleration
		// computation'
		// which is average of each state vector estimate
		prevSpeedX = updatedSource.getSpeedX();
		prevSpeedY = updatedSource.getSpeedY();
		prevSpeedZ = updatedSource.getSpeedZ();

		sourceDataModels[0] = updatedSource; // Override correlated input source
		// to reflect newly fused data vector
		try {
			// Send to math Functions and output and database
			// Send updatedSource to
			db.updateCompositeBuilder(updatedSource);
			// analysisModule.setSource(updatedSource);
		} catch (SQLException ex) {
			Logger.getLogger(ObjectRefinementModule.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	/**
	 * Method for setting the confidence interval to user defined setting.
	 * 
	 * @param confidenceProbability
	 *            :New confidence interval for gating filter
	 */
	public void setConfidenceProbability(double confidenceProbability) {
		this.CONFIDENCE_PROBABILITY = confidenceProbability;
	}

	/**
	 * This method computes the prediction phase in the Kalman filter Prediction
	 * does two things, 1. Project the state estimate in the future x(k) =
	 * state_x_post
	 * 
	 * @return state_pre: state vector estimate without using measurements
	 */
	public Matrix computePredictionPhase() {

		// Step 1: Project the state ahead
		// state_pre = state_transition * state_post

		// System.out.println("state_x_post before Prediction:: "
		// +state_x_post);

		state_x_pre = state_transition.times(state_x_post);
		// System.out.println("State_x_pre predicted ahead:: " + state_x_pre);

		// Next compute the covariance for this state estimate, P
		// state_x_pre = p(k) =temp 1*state_transtion(transpose) + Q
		Matrix temp = state_transition.times(covariance_p_post);

		covariance_p_pre = temp.gemm(state_transition.transpose(),
				covariance_q, 1, 1);

		return state_x_pre;
	}

	/**
	 * This function computes the Correction phase of the Kalman filter Computes
	 * Kalman gain and the covariance for the posteriori estimate
	 * 
	 * @return: corrected state estimate using the new measurements
	 */
	public Matrix computeCorrectionPhase(SourceDataModel s1) {

		// Matrix updatedMeasurement = new Matrix(measurementUpdate);
		Matrix updatedMeasurement = assembleStateEst(s1);

		Matrix temp_state_x_post;
		// Check gating Filter if current measurement is faulty.
		if (gatingFilter(updatedMeasurement)) {
			// Send to output and database state_x_pre(Predicted State)

			temp_state_x_post = state_x_pre;
		} else { // Compute correction based on current measurement

			// Create measurementNoise covariance
			// *NOTE* Since acceleration is calculated, no noise is associated
			// with them.
			// Thus, identity is used.
			double[][] measurementNoiseCovariance = {
					{ s1.getErrorX(), 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, s1.getErrorY(), 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, s1.getErrorZ(), 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, s1.getErrorX(), 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, s1.getErrorY(), 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, s1.getErrorZ(), 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 1, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 1 } };
			covariance_r = new Matrix(measurementNoiseCovariance);

			// Step1: Compute kalman gain matrix
			// // temp1 = H*P'(k)
			Matrix temp1 = measurement_matrix.times(covariance_p_pre);

			// temp2 = temp2*Ht + R
			Matrix temp2 = temp1.gemm(measurement_matrix.transpose(),
					covariance_r, 1, 1);

			// temp3 = inv(temp3)*temp2 = Kt(k)
			Matrix temp3 = temp2.solve(temp1);

			kalmanGain = temp3.transpose();

			// Step (2) Update estimate with measurement matrix
			// temp4 = z(k) - H*x'(k)
			Matrix temp4 = measurement_matrix.gemm(state_x_pre,
					updatedMeasurement, -1, 1);

			// x(k) = x'(k) + K(k)*temp5
			temp_state_x_post = kalmanGain.gemm(temp4, state_x_pre, 1, 1);

			// (3) Update the error covariance.
			// P(k) = P'(k) - K(k)*temp2
			covariance_p_post = kalmanGain.gemm(temp1, covariance_p_pre, -1, 1);

		}
		return temp_state_x_post;

	}

	/**
	 * This function uses a Gating Technique to appropriate the fused data
	 * received from the objectRefinement module. Only latitude,,Longitude, and
	 * Altitude fields are chosen for evaluation, since they have been chosen as
	 * critical object tracking fields
	 * 
	 * Step 1. Compute (Expected[x] - measurement[x]) / Expected[x] Step 2. IF
	 * computed value > specified confidence probability, this measurement is
	 * not chosen for evaluation.
	 * 
	 * @return Boolean: Whether this measurement is worth investigating.
	 */
	private boolean gatingFilter(Matrix updateMeasurement) {

		// Compute (Expected - Measured)/ Expected.

		Matrix difference = state_x_pre.minus(updateMeasurement);
		Matrix probability = difference.arrayRightDivide(state_x_pre);
		// If this probability is within the allowedProbability,
		// accept the measurement;
		// *NOTE* Only the x values are defined critical test fields, thus
		// only testing x,y,z values

		double[][] measurementMatrix = probability.getArray();
		for (int i = 0; i < 3; i++) {

			if (Math.abs(measurementMatrix[i][0]) > CONFIDENCE_PROBABILITY) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This function takes a source object, and creates the new state vector
	 * from it.
	 * 
	 * @param s1
	 *            : Source to make a state estimate from.
	 * @return: Matrix corresponding to the new state vector obtained from the
	 *          Source.
	 */
	private Matrix assembleStateEst(SourceDataModel s1) {

		// Compute acceleration for our target, using acceleration definition,
		// a=(Vk -Vk-1)/Time_Difference
		double accelerationX = ((s1.getSpeedX() - prevSpeedX) / TIME_DIFFERENCE_OF_SOURCES);
		double accelerationY = ((s1.getSpeedY() - prevSpeedY) / TIME_DIFFERENCE_OF_SOURCES);
		double accelerationZ = ((s1.getSpeedZ() - prevSpeedZ) / TIME_DIFFERENCE_OF_SOURCES);

		// Initial state estimate with new source measurements
		double[][] stateEst = { { s1.getPositionX() },
				{ s1.getPositionY() }, { s1.getPositionZ() },
				{ s1.getSpeedX() }, { s1.getSpeedY() }, { s1.getSpeedZ() },
				{ accelerationX }, { accelerationY }, { accelerationZ } };
		return new Matrix(stateEst);
	}

	/**
	 * This helper method augments the supplied source with the newly correlated
	 * state vector estimates from the Kalman filter.
	 * 
	 * @param updatedSource
	 *            : Source to update from the newly correlated state vector.
	 * @return: The source representing our correlated stateVector
	 */
	private SourceDataModel reconstructSource(SourceDataModel updatedSource) {

		double[][] currStateVector = state_x_post.getArray();

		updatedSource.setPositionX(currStateVector[0][0]); // PositionLat
		updatedSource.setPositionY(currStateVector[1][0]); // PositionLong
		updatedSource.setPositionZ(currStateVector[2][0]); // PositionAlt

		updatedSource.setSpeedX(currStateVector[3][0]); // SpeedX
		updatedSource.setSpeedY(currStateVector[4][0]); // SpeedY
		updatedSource.setSpeedZ(currStateVector[5][0]); // SpeedZ

		return updatedSource;
	}
}

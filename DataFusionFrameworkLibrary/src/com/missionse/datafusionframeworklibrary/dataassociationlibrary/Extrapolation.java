package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class Extrapolation will linear extrapolate the input coordinates forward or backward.
 * The extrapolated position will be returned.
 */
public class Extrapolation {

	public Extrapolation() {
	}

	/**
	 * This method will linear extrapolate the input Cartesian coordinates
	 * forward or backward. This method can be overloaded to support
	 * extrapolation in other coordinate systems (ig. polar)
	 */
	public void extrapolateTrack() {
		double x = 0.0, y = 0.0, z = 0.0;
		double vx = 0.0, vy = 0.0, vz = 0.0;
		double t = 0.0;
		linearExtrapolate(x, y, z, vx, vy, vz, t);

	}

	/**
	 * This method will linearly extrapolate position.
	 */
	private void linearExtrapolate(double x, double y, double z, double vx,
			double vy, double vz, double t) {
		double xposition = 0.0, yposition = 0.0, zposition = 0.0;
		xposition = xposition(x, vx, t);
		yposition = yposition(y, vy, t);
		zposition = zposition(z, vz, t);

	}

	/**
	 * extrapolated x = x + (vx * delta t)
	 */
	private double xposition(double x, double vx, double t) {
		return (x + (vx * t));

	}

	/**
	 * extrapolated y = y + (vy * delta t)
	 */
	private double yposition(double y, double vy, double t) {
		return (y + (vy * t));

	}

	/**
	 * extrapolated z = z + (vz * delta t)
	 */
	private double zposition(double z, double vz, double t) {
		return (z + (vz * t));

	}

}

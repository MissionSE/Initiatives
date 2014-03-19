package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class kinematicAssociation will perform necessary kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class KinematicTest {
	private double range_gate = 3.0;
	private double speed_gate = 0.05;
	private double height_gate = 1.67;
	private double course_gate = 5.0;
	
    public KinematicTest()
    {}

	/**
	 * This method will perform necessary kinematic tests for data association.
	 */
	public boolean kinematicTest()
	{
		boolean valid = false;
	    double x1, x2, y1, y2, z1, z2;
	    double vx1, vx2, vy1, vy2, vz1, vz2;
	    double c1, c2;
	    double h1, h2;
//	    valid = XYZposAssociation(x1, y1, z1, x2, y1, z2);
//		valid = XYZvelAssociation(vx1, vy1, vz1, vx2, vy2, vz2);
//		valid = heightAssociation(h1, h2);
	    valid = XYZposAssociation(41.99417, -119.305344, 2500.0, 41.9942, -119.305333, 2500.0);
		valid = XYZvelAssociation(0.3, 0.3, 0.2, 0.3, 0.3, 0.2);

		return valid;

	}
	
	/**
	 * This method will perform position test for data association .
	 */
	private boolean XYZposAssociation(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double distance;  
		distance  = distance(x1,y1,z1,x2,y2,z2);
        System.out.println("KinematicTest XYZposAssociation: "+distance);        // create return array of candidate is'd
		
        // compare position difference with range gate
		if (distance < range_gate)
			return true;
		else
			return false;

	}
				
	/**
	 * distance = square root of [(x2-x1)squared + (y2-y1)squared + (z2-z1)squared]
	 */		
	private double distance(double x1, double y1, double z1, double x2, double y2, double z2)  
    {  
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1));  
          
    }  
  	
	/**
	 * This method will perform velocity test for data association .
	 */
	private boolean XYZvelAssociation(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2)
	{
		double velocity;  
		velocity  = velocity(vx1,vy1,vz1,vx2,vy2,vz2);				
        System.out.println("KinematicTest XYZvelAssociation: "+velocity);        // create return array of candidate is'd

        // compare velocity difference with speed gate
		if (velocity < speed_gate)
			return true;
		else
			return false;

	}
	
	/**
	 * velocity = square root of [(vx2-vx1)squared + (vy2-vy1)squared + (z2-z1)squared]
	 */		
	private double velocity(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2)  
    {  
        return Math.sqrt((vx2-vx1)*(vx2-vx1) + (vy2-vy1)*(vy2-vy1) + (vz2-vz1)*(vz2-vz1));  
          
    }  
  
	/**
	 * This method will perform height test for data association .
	 * @param h2 
	 * @param h1 
	 */
	private boolean heightAssociation(double h1, double h2)
	{
		double height = 0.0;
		height = height(h1, h2);
        System.out.println("KinematicTest height: "+height);        // create return array of candidate is'd
		
        // compare altitude difference with height gate
		if (height < height_gate)
			return true;
		else
			return false;

	}
	
	/**
	 * this method calculates the difference between height
	 * height = height1 - height2
	 */
	private double height(double h1, double h2)
	{
		double diff = h1 - h2;
		
		return Math.abs(diff);

	}

	/**
	 * This method will perform course test for data association .
	 */
	private boolean courseAssociation(double c1, double c2)
	{
		double course = 0.0;
		course = course(c1, c2);	
		
        // compare course difference with course gate
		if (course < course_gate)
			return true;
		else
			return false;

	}
	
	/**
	 * this method calculates the difference between courses then adjusts the difference
	 * course = course1 - course2
	 */
	private double course(double c1, double c2)
	{
		double diff = c1 - c2;
		
		if (diff > 180.0)			
			diff = diff - 360.0;
		else if (diff < -180.0)
			diff = diff + 360.0;
		
		return Math.abs(diff);

	}

	
}

package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class kinematicAssociation will perform necessary kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class KinematicEvaluation {

    public KinematicEvaluation()
    {}

	/**
	 * This method will perform necessary kinematic tests for data association.
	 */
	public boolean kinematicAssociation()
	{
		
	    double x1, x2, y1, y2, z1, z2;
	    double vx1, vx2, vy1, vy2, vz1, vz2;
	    XYZposAssociation(x1, x2, y1, y2, z1, z2);
		XYZvelAssociation(vx1, vx2, vy1, vy2, vz1, vz2);
		heightAssociation();
		courseAssociation();
		return false;

	}
	
	/**
	 * This method will perform position test for data association .
	 */
	private boolean XYZposAssociation(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double distance;  
		distance  = distance (x1,y1,z1,x2,y2,z2);
				
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
		velocity  = velocity (vx1,vy1,vz1,vx2,vy2,vz2);				

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
	 */
	private boolean heightAssociation()
	{
		return false;

	}
	
	/**
	 * This method will perform course test for data association .
	 */
	private boolean courseAssociation()
	{
		return false;

	}
	
}

package edu.nr.lib;

import edu.nr.lib.navx.NavX;

public class AngleGyroCorrection extends GyroCorrection
{
	private double initialAngle;
	
	public double getAngleErrorDegrees()
	{
		//Error is just based off initial angle
    	return (NavX.getInstance().getYaw(AngleUnit.DEGREE) - initialAngle);
	}
	
	public void reset()
	{
		initialAngle = NavX.getInstance().getYaw(AngleUnit.DEGREE);
	}
}

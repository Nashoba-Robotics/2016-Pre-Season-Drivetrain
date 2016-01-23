package edu.nr.lib;

public class AngleGyroCorrection extends GyroCorrection
{
	private double initialAngle;
	
	public double getAngleErrorDegrees()
	{
		//Error is just based off initial angle
    	return (NRMath.radToDeg(FieldCentric.getInstance().getAngleRadians()) - initialAngle);
	}
	
	public void reset()
	{
		initialAngle = NRMath.radToDeg(FieldCentric.getInstance().getAngleRadians());
	}
}

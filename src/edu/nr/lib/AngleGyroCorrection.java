package edu.nr.lib;

import edu.nr.lib.navx.BaseNavX;
import edu.nr.lib.navx.NavX;

public class AngleGyroCorrection extends GyroCorrection {

	private double initialAngle;
	double goalAngle;
	BaseNavX navx;
	
	public AngleGyroCorrection(double angle, BaseNavX navx2) {
		if(navx2 == null) {
			this.navx = NavX.getInstance();
		}
		this.navx = navx2;
		goalAngle = angle;
		initialAngle = navx2.getYaw(AngleUnit.DEGREE);
	}
	
	public AngleGyroCorrection(double angle) {
		this(angle, NavX.getInstance());
	}
	
	public AngleGyroCorrection(BaseNavX navx) {
		this(0, navx);
	}
	
	public AngleGyroCorrection() {
		this(0);
	}
	
	public double get() {
		return getAngleErrorDegrees();
	}
	
	public double getAngleErrorDegrees()
	{
		//Error is just based off initial angle
    	return (navx.getYaw(AngleUnit.DEGREE) - initialAngle) + goalAngle;
	}
	
	public void reset()
	{
		initialAngle = navx.getYaw(AngleUnit.DEGREE);
	}
}

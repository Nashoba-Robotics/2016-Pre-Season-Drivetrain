package edu.nr.lib;

import edu.nr.lib.navx.BaseNavX;
import edu.nr.lib.navx.NavX;
import edu.nr.lib.navx.TestNavX;

public class AngleGyroCorrection extends GyroCorrection {

	private double initialAngle;
	double goalAngle;
	BaseNavX navx;
	
	AngleUnit unit;
	
	public AngleGyroCorrection(double angle, BaseNavX navx, AngleUnit unit) {
		if(navx == null) {
			this.navx = NavX.getInstance();
		}
		this.navx = navx;
		goalAngle = angle;
		this.unit = unit;
		initialAngle = navx.getYaw(unit);
	}
	
	public AngleGyroCorrection(double angle, AngleUnit unit) {
		this(angle, NavX.getInstance(), unit);
	}
	
	public AngleGyroCorrection(BaseNavX navx) {
		this(0, navx, AngleUnit.DEGREE);
	}
	
	public AngleGyroCorrection() {
		this(0, AngleUnit.DEGREE);
	}
	
	public AngleGyroCorrection(BaseNavX navx, AngleUnit unit) {
		this(0, navx, unit);
	}

	public double get() {
		return getAngleErrorDegrees();
	}
	
	public double getAngleErrorDegrees()
	{
		//Error is just based off initial angle
    	return (navx.getYaw(unit) - initialAngle) + goalAngle;
	}
	
	public void reset()
	{
		initialAngle = navx.getYaw(unit);
	}
}

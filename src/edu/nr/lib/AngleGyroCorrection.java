package edu.nr.lib;

import edu.nr.lib.navx.NavX;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AngleGyroCorrection extends GyroCorrection implements PIDSource
{
	private double initialAngle;
	double goalAngle;
	NavX navx;
	
	public AngleGyroCorrection(double angle, NavX navx) {
		if(navx == null) {
			this.navx = NavX.getInstance();
		}
		this.navx = navx;
		goalAngle = angle;
		initialAngle = navx.getYaw(AngleUnit.DEGREE);
	}
	
	public AngleGyroCorrection(double angle) {
		this(angle, NavX.getInstance());
	}
	
	public AngleGyroCorrection(NavX navx) {
		this(0, navx);
	}
	
	public AngleGyroCorrection() {
		this(0);
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
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		System.err.println("Can't set the PID source type of an angle gyro correction - it is always kDisplacement");
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getAngleErrorDegrees();
	}
}

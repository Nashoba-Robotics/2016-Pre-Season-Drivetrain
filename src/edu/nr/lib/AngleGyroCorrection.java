package edu.nr.lib;

import edu.nr.lib.navx.NavX;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AngleGyroCorrection extends GyroCorrection implements PIDSource
{
	private double initialAngle;
	
	PIDSourceType type;
	
	public AngleGyroCorrection(double angle) {
		initialAngle = NavX.getInstance().getYaw(AngleUnit.DEGREE) + angle;
		type = PIDSourceType.kDisplacement;
	}
	
	public AngleGyroCorrection() {
		initialAngle = NavX.getInstance().getYaw(AngleUnit.DEGREE);
		type = PIDSourceType.kDisplacement;
	}
	
	public double getAngleErrorDegrees()
	{
		//Error is just based off initial angle
    	return (NavX.getInstance().getYaw(AngleUnit.DEGREE) - initialAngle);
	}
	
	public void reset()
	{
		initialAngle = NavX.getInstance().getYaw(AngleUnit.DEGREE);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		return getAngleErrorDegrees();
	}
}

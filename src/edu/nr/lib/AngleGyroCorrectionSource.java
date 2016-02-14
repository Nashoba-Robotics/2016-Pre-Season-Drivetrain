package edu.nr.lib;

import edu.nr.lib.navx.NavX;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AngleGyroCorrectionSource extends AngleGyroCorrection implements PIDSource {

	PIDSourceType type;
	
	public AngleGyroCorrectionSource(double angle, NavX navx) {
		super(angle, navx);
		type = PIDSourceType.kDisplacement;
	}
	
	public AngleGyroCorrectionSource(double angle) {
		super(angle);
	}
	
	public AngleGyroCorrectionSource(NavX navx) {
		super(navx);
	}
	
	public AngleGyroCorrectionSource() {
		super();
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
		return super.getAngleErrorDegrees();
	}
}

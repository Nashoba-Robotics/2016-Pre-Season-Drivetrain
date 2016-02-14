package edu.nr.lib;

import edu.nr.lib.navx.BaseNavX;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AngleGyroCorrectionSource extends AngleGyroCorrection implements PIDSource {

	PIDSourceType type;
	
	public AngleGyroCorrectionSource(double angle, BaseNavX navx) {
		super(angle, navx);
		type = PIDSourceType.kDisplacement;
	}
	
	public AngleGyroCorrectionSource(double angle) {
		super(angle);
	}
	
	public AngleGyroCorrectionSource(BaseNavX navx) {
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

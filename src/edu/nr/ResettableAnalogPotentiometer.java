package edu.nr;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class ResettableAnalogPotentiometer extends AnalogPotentiometer {

	double bottomPos;
	
	public ResettableAnalogPotentiometer(int intakeArmPot) {
		super(intakeArmPot);
	}

	@Override
	public double get() {
		return super.get() - bottomPos;
	}
	
	public void reset() {
		bottomPos = super.get();
	}
}

package edu.nr.lib;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class ResettableAnalogPotentiometer extends AnalogPotentiometer {

	double bottomPos;
	double scale = 1;
	
	public ResettableAnalogPotentiometer(int intakeArmPot) {
		super(intakeArmPot);
	}

	@Override
	public double get() {
		return (super.get() - bottomPos) / scale;
	}
}

package edu.nr.lib;

import edu.wpi.first.wpilibj.AnalogInput;

public class AnalogIRSensor extends AnalogInput {

	public AnalogIRSensor(int channel) {
		super(channel);
	}
	
	public double getDistance() {
		double d = getVoltage();
		//TODO: getDistance with IR sensor.
		return d;
	}

}

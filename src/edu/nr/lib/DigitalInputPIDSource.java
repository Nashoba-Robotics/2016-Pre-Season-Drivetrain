package edu.nr.lib;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DigitalInputPIDSource extends DigitalInput implements PIDSource {

	Counter counter;
	
	PIDSourceType PIDtype = PIDSourceType.kRate;
	
	public DigitalInputPIDSource(int port) {
		this(port, 1);
	}
	
	public DigitalInputPIDSource(int port, double distancePerPulse) {
		super(port);

		counter = new Counter(port);
		counter.setDistancePerPulse(distancePerPulse);
	}	
	
	public void setSamplesToAverage(int samples) {
		counter.setSamplesToAverage(samples);
	}
	
	public int getSamplesToAverage() {
		return counter.getSamplesToAverage();
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		PIDtype = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDtype;
	}
	
	public void setDistancePerPulse(double distancePerPulse) {
		counter.setDistancePerPulse(distancePerPulse);
	}

	@Override
	public double pidGet() {
		if(PIDtype == PIDSourceType.kRate)
			return counter.getRate();
		else
			return counter.getDistance();
	}
	
	public double getRate() {
		return counter.getRate();
	}
	
	public double getDistance() {
		return counter.getDistance();
	}

}

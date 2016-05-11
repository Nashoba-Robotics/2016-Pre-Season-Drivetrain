package edu.nr.lib;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OneDimensionalMotionProfiler extends TimerTask {

	private final Timer timer;
	
	//In milliseconds
	private final long period;
	private static final long defaultPeriod = 5; //200 Hz 
	
	private long prevTime;
	
	private boolean enabled = false;
	private PIDOutput out;
	private PIDSource source;
	
	private double maxAccel, maxVel, goalPosition;
	
	public OneDimensionalMotionProfiler(PIDOutput out, PIDSource source, double maxAccel, double maxVel, double goalPosition, long period) {
		this.out = out;
		this.source = source;
		this.period = period;
		
		this.maxAccel = maxAccel;
		this.maxVel = maxVel;
		this.goalPosition = goalPosition;
		timer = new Timer();
		timer.schedule(this, 0, this.period);
		prevTime = System.currentTimeMillis();
		

	}
	
	public OneDimensionalMotionProfiler(PIDOutput out, PIDSource source, double maxAccel, double maxVel, double goalPosition) {
		this(out, source, maxAccel, maxVel, goalPosition, defaultPeriod);
	}
	
	@Override
	public void run() {
		if(enabled) {
			double dt = System.currentTimeMillis() - prevTime;

			double output = 0;
			
			//TODO: Do stuff to calculate output
			
			out.pidWrite(output);
			
			SmartDashboard.putNumber("Motion Profiler Output", output);
		}
		prevTime = System.currentTimeMillis();
	}
		
	/**
	 * Stop the profiler from running
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Start the profiler running
	 */
	public void enable() {
		enabled = true;
	}
	
	/**
	 * Reset the previous time to the current time.
	 * TODO: reset motion profiler
	 * Doesn't disable the controller
	 */
	public void reset() {

		
		prevTime = System.currentTimeMillis();
	}

	/**
	 * Sets the PIDOutput for the profiler
	 * @param out
	 */
	public void setOut(PIDOutput out) {
		this.out = out;
	}

	/**
	 * Sets the PIDSource for the profiler
	 * @param source
	 */
	public void setSource(PIDSource source) {
		this.source = source;
	}

	/**
	 * Gets the current set maximum acceleration
	 * @return the maximum acceleration
	 */
	public double getMaxAccel() {
		return maxAccel;
	}

	/**
	 * Sets the maximum acceleration
	 * @param maxAccel maximum acceleration
	 */
	public void setMaxAccel(double maxAccel) {
		this.maxAccel = maxAccel;
	}

	/**
	 * Gets the current set maximum velocity
	 * @return the maximum velocity
	 */
	public double getMaxVel() {
		return maxVel;
	}

	/**
	 * Sets the maximum velocity
	 * @param maxVel maximum velocity
	 */
	public void setMaxVel(double maxVel) {
		this.maxVel = maxVel;
	}

}

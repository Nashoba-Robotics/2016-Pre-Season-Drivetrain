package edu.nr.robotics.subsystems.drive;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID extends TimerTask {

	Timer timer;
	
	double p, i, d;
	
	double prevError = 0;
	double prevTime;
	
	double totalError = 0;
	
	boolean enabled = false;
	
	PIDOutput output;
	PIDSource source;
	
	double setpoint = 0;
		
	long period = 1; //in milliseconds

	double integralDisableDistance;
	
	double dampenRate;
	
	/**
	 * @param integralDisableDistance The error beyond which (greater than) there is no integral term
	 * @param output the output 
	 * @param source the source
	 * @param setpoint the setpoint value
	 * @param dampenRate the value to multiply the total error by every loop
	 */
	public PID(double integralDisableDistance, PIDOutput output, PIDSource source, double setpoint, double dampenRate) {
		this.output = output;
		this.source = source;
		this.integralDisableDistance = integralDisableDistance;
		this.setpoint = setpoint;
		this.dampenRate = dampenRate;
		timer = new Timer();
		timer.schedule(this, 0, period);
		prevTime = System.currentTimeMillis();
	}
	
	/**
	 * Run the calculation.
	 * 
	 * If the robot is enabled, set the output to the calculated value
	 */
	@Override
	public void run() {
		double error = getError();

		if(enabled) {
			double output = 0;
	    	
	    	final double TURN_P = SmartDashboard.getNumber("Turn P");
	    	final double TURN_I = SmartDashboard.getNumber("Turn I");
	    	final double TURN_D = SmartDashboard.getNumber("Turn D");
	    	dampenRate = SmartDashboard.getNumber("Turn Dampen Rate");
	
			//P value
			output += TURN_P*(error);
	
	    	//I value
			if(Math.abs(error) > integralDisableDistance) {
				totalError = 0;
			} else {
				totalError += TURN_I*(error);
			}
			
			/*if(Math.signum(error) != Math.signum(totalError)) {
				totalError = 0;
			} Replaced with dampening */
			
			//Dampen the total error so that it doesn't get too huge
			// For example, if we were three units away, and go 3,2,1,0, 
			// the total error would be -6 and so we would go way past zero.
			// Dampening makes it so that at the end we're only at something
			// like -2, so we only go a little past and then quickly come back.
			totalError *= dampenRate;
			
			output += totalError;
			
			//D value
			double errorChange = error - prevError;
			double timeChange = System.currentTimeMillis() - prevTime;
			
			output += TURN_D * (errorChange/timeChange);
			
			//Output
			this.output.pidWrite(output);
			
	    	SmartDashboard.putNumber("Drive Turn Error", error);
	    	SmartDashboard.putNumber("Drive Turn Total Error", totalError);
	    	SmartDashboard.putNumber("Drive Turn Output", output);
	    	
		}
		
    	prevError = error;
		prevTime = System.currentTimeMillis();
	}
	
	/**
	 * Get the error of the PID loop
	 * 
	 * 	It is the setpoint value subtracted from the current value
	 * 
	 * @return
	 */
	double getError() {
		return source.pidGet() - setpoint;
	}

	/**
	 * Stop the PID from running
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Start the PID running
	 */
	public void enable() {
		enabled = true;
	}
	
	/**
	 * Reset the total error to zero, set the previous error to the current error, and set the previous time to the current time.
	 */
	public void reset() {
		totalError = 0;
		prevError = getError();
		prevTime = System.currentTimeMillis();
	}
	
	/**
	 * Set the setpoint and reset the PID
	 * @param setpoint
	 */
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
		reset();
		prevError = getError();
		
	}

}

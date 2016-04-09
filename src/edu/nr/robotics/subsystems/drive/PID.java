package edu.nr.robotics.subsystems.drive;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID extends TimerTask {

	Timer timer;
	
	double p, i;
	
	double totalError = 0;
	
	boolean enabled = false;
	
	PIDOutput output;
	PIDSource source;
	
	double goal = 0;
	
	double minimumOutput = 0;
	
	long period = 1; //in milliseconds

	double integralDisableDistance;
	
	public PID(double integralDisableDistance, PIDOutput output, PIDSource source, double goal, double minimumOutput) {
		this.output = output;
		this.integralDisableDistance = integralDisableDistance;
		this.goal = goal;
		this.minimumOutput = minimumOutput;
		timer = new Timer();
		timer.schedule(this, 0, period);
	}
	
	
	@Override
	public void run() {
		if(enabled) {
			double output = 0;
			double error = source.pidGet() - goal;
	    	
	    	final double TURN_P = SmartDashboard.getNumber("Turn P");
	    	final double TURN_I = SmartDashboard.getNumber("Turn I");
	
			if(Math.abs(error) > integralDisableDistance) {
				totalError = 0;
			} else {
				totalError += TURN_I*(error);
			}
			
			if(Math.signum(error) != Math.signum(totalError)) {
				totalError = 0;
			}
			
			output += TURN_P*(error);
	
			output += totalError;
			if(Math.abs(output) < 0.1) {
				output = 0.1 * Math.signum(output);
			} else if(Math.abs(output) > 0.3) {
				output = 0.3 * Math.signum(output);
			}
			
			output *= -1;
			if(Math.abs(output) < Math.abs(minimumOutput)) {
				output = minimumOutput * Math.signum(output);
			}
			this.output.pidWrite(output);
			
	    	SmartDashboard.putNumber("Drive Turn Error", error);
	    	SmartDashboard.putNumber("Drive Turn Total Error", totalError);
	    	SmartDashboard.putNumber("Drive Turn Output", output);
		}
	}
	
	double getError() {
		return source.pidGet() - goal;
	}


	public void disable() {
		enabled = false;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void reset() {
		totalError = 0;
	}
	
	public void setGoal(double goal) {
		this.goal = goal;
	}

}

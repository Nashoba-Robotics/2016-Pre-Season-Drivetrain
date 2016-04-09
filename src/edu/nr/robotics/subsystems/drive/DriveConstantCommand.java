package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class DriveConstantCommand extends NRCommand {

	boolean PID;
	double left, right;
	
	
    public DriveConstantCommand(boolean PID, boolean left, boolean right, double val) {
    	this.PID = PID;
    	this.left = left ? val : 0;
    	this.right = right ? val : 0;
        requires(Drive.getInstance());
    }

	public DriveConstantCommand(boolean PID, double left, double right) {
		this.PID = PID;
		this.left = left;
		this.right = right;
        requires(Drive.getInstance());
	}

	@Override
	protected void onStart() {
		Drive.getInstance().setPIDEnabled(PID);
		Drive.getInstance().setMotorSpeed(left, right);
	}
	
	@Override
	protected boolean isFinishedNR() {
		return false;
	}
    
}

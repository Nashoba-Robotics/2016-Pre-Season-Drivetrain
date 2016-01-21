package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;

/**
 *
 */
public class DriveConstantCommand extends CMD {

	boolean PID;
	double left, right;
	
	
    public DriveConstantCommand(boolean PID, boolean left, boolean right, double val) {
    	this.PID = PID;
    	this.left = left ? val : 0;
    	this.right = right ? val : 0;
        requires(Drive.getInstance());
    }

	@Override
	protected void onStart() {
		if(PID)
			Drive.getInstance().setPIDSetpoint(left, right);
		else
			Drive.getInstance().setRawMotorSpeed(left, right);
	}

	@Override
	protected void onExecute() {
		
	}

	@Override
	protected void onEnd(boolean interrupted) {
		if(PID)
			Drive.getInstance().setPIDSetpoint(0, 0);
		else
			Drive.getInstance().setRawMotorSpeed(0, 0);	
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
    
}

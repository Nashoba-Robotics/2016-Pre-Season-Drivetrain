package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class IntakeRollerSpeedCommand extends NRCommand {

	double val;
	
    public IntakeRollerSpeedCommand(double val) {
    	this.val = val;
    	requires(IntakeRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void onStart() {
    	IntakeRoller.getInstance().setRollerSpeed(val);
    }
    
    public boolean isFinishedNR() {
    	return false;
    }
}

package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.CMD;

/**
 *
 */
public class IntakeRollerSpeedCommand extends CMD {

	double val;
	
    public IntakeRollerSpeedCommand(double val) {
    	this.val = val;
    	requires(IntakeRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	IntakeRoller.getInstance().setRollerSetpoint(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}

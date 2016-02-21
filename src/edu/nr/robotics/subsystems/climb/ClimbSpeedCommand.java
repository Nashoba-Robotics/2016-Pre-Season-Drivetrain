package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ClimbSpeedCommand extends NRCommand {

	double val;
	
    public ClimbSpeedCommand(double val) {
    	this.val = val;
    	requires(Climb.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Climb.getInstance().setMotorValue(val);
    }
}

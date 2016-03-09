package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ElevatorSpeedCommand extends NRCommand {

	double val;
	
    public ElevatorSpeedCommand(double val) {
    	this.val = val;
    	requires(Elevator.getInstance());
    }

    // Called just before this Command runs the first time
    protected void onStart() {
    	Elevator.getInstance().setMotorValue(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
        return false;
    }
}

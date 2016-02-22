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
}

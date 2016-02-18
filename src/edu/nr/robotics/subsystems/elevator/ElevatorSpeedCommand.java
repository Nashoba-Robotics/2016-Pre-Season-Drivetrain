package edu.nr.robotics.subsystems.elevator;

import edu.nr.lib.CMD;

/**
 *
 */
public class ElevatorSpeedCommand extends CMD {

	double val;
	
    public ElevatorSpeedCommand(double val) {
    	this.val = val;
    	requires(Elevator.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Elevator.getInstance().setMotorValue(val);
    }
}

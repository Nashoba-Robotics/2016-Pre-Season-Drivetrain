package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ElevatorUnlatchCommand extends NRCommand {
	
	long startTime;
	
    public ElevatorUnlatchCommand() {
    	requires(Elevator.getInstance());
    }

    // Called just before this Command runs the first time
    protected void onStart() {
    	Elevator.getInstance().setMotorValue(-1);
    	startTime = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
        return System.currentTimeMillis() - startTime > 1000;
    }
}

package edu.nr.robotics.subsystems.elevator;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ElevatorWaitForMotorStallTimeCommand extends NRCommand {

	long timeStalling = 0;
	
	long prevTime;
	
	long reqTime;
	
    public ElevatorWaitForMotorStallTimeCommand(long reqTime) {
    	this.reqTime = reqTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	prevTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	if(!Elevator.getInstance().isMoving()) {
    		timeStalling = System.currentTimeMillis() - prevTime;
    	} else {
    		timeStalling = 0;
    	}
    	
    	prevTime = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeStalling > reqTime;
    }
}

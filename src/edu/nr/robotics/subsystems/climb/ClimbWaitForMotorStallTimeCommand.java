package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ClimbWaitForMotorStallTimeCommand extends NRCommand {

	long timeStalling = 0;
	
	long prevTime;
	
	long reqTime;
	
    public ClimbWaitForMotorStallTimeCommand(long reqTime) {
    	this.reqTime = reqTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	prevTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	if(!Climb.getInstance().isMoving()) {
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

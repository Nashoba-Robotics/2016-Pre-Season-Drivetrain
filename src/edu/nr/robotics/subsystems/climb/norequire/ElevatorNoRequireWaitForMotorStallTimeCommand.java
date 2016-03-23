package edu.nr.robotics.subsystems.climb.norequire;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.climb.Elevator;

/**
 *
 */
public class ElevatorNoRequireWaitForMotorStallTimeCommand extends NRCommand {

	long timeStalling = 0;
	
	long prevTime;
	
	long reqTime;
	
	/**
	 * 
	 * @param reqTime in seconds
	 */
    public ElevatorNoRequireWaitForMotorStallTimeCommand(long reqTime) {
    	this.reqTime = reqTime*1000;
    	requires(ResetElevatorSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void onStart() {
    	prevTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void onExecute() {
    	if(!Elevator.getInstance().isMoving()) {
    		timeStalling = System.currentTimeMillis() - prevTime;
    	} else {
    		timeStalling = 0;
    	}
    	
    	prevTime = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinishedNR() {
        return timeStalling > reqTime;
    }
}

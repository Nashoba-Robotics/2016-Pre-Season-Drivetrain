package edu.nr.robotics.subsystems.elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorWaitForMotorStallTimeCommand extends Command {

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
    protected void execute() {
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

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

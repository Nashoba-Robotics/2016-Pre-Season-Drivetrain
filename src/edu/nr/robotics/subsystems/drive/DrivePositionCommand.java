package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;
import edu.nr.lib.FalconPathPlanner;

/**
 *
 */
public class DrivePositionCommand extends CMD {

	double[][] waypoints;
	double totalTime; //seconds
	double timeStep; //period of control loop on Rio, seconds
	double robotTrackWidth; //distance between left and right wheels, feet
	
    public DrivePositionCommand(double[][] waypoints, double totalTime, double timeStep, double robotTrackWidth) {
    	this.waypoints = waypoints;
    	this.totalTime = totalTime;
    	this.timeStep = timeStep;
    	this.robotTrackWidth = robotTrackWidth;
		final FalconPathPlanner path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);		

        requires(Drive.getInstance());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

	@Override
	protected void onStart() {
		//pass .smoothRightVelocity[1], .smoothLeftVelocity[1] to the corresponding speed controllers on the robot 
	}

	@Override
	protected void onExecute() {
		//step through each setPoint on the speed controller
	}

	@Override
	protected void onEnd(boolean interrupted) {
		
	}
    
}

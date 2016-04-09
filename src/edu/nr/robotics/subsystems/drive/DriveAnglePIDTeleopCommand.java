package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.network.AndroidServer;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDTeleopCommand extends NRCommand {

	PID pid;
	
	double angle;
	AngleController controller;
	AngleGyroCorrectionSource correction;
	
	double integralDisableDistance = 5;

	double accuracyFinishCount = 0;
	double currentCount = 0;
	
	boolean useJetson;
	
	boolean goodToGo = true;

	
    public DriveAnglePIDTeleopCommand(boolean useJetson) {
    	this.correction = new AngleGyroCorrectionSource(AngleUnit.DEGREE);
    	this.useJetson = useJetson;
    	requires(Drive.getInstance());
		pid = new PID(integralDisableDistance, controller, correction, 0, RobotMap.MINIMUM_TURN);
    }
    
    public DriveAnglePIDTeleopCommand(double angle, AngleUnit unit) {
    	this(angle, new AngleGyroCorrectionSource(unit));    	
    }

    public DriveAnglePIDTeleopCommand(double angle, AngleGyroCorrectionSource correction) {
    	this.angle = angle;
    	this.correction = correction;
    	this.useJetson = false;
    	requires(Drive.getInstance());
		pid = new PID(integralDisableDistance, controller, correction, angle, RobotMap.MINIMUM_TURN);
	}

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
    	if(!goodToGo)
    		return true;
    	if(Math.abs(pid.getError()) < RobotMap.TURN_THRESHOLD) {
    		currentCount++;
    	} else {
    		currentCount = 0;
    	}
    	return currentCount > accuracyFinishCount;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		pid.disable();
		pid.reset();
		goodToGo = true;
	}

	@Override
	protected void onStart() {
		
		if(useJetson) {
			if(!AndroidServer.getInstance().goodToGo()) { 
	    		System.out.println("Android connection not good to go");
	    		goodToGo = false;
	    		return;
	    	}
			
			angle = AndroidServer.getInstance().getTurnAngle();

			if(Math.abs(angle) < RobotMap.TURN_THRESHOLD) {
				goodToGo = false;
				return;
			}
			
			pid.setGoal(angle);

		}
		
		Drive.getInstance().setPIDEnabled(true);
		controller = new AngleController();
		correction.reset();
		pid.enable();
	}
}

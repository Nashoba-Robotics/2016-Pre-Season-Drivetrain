package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	AngleController controller;
	AngleGyroCorrectionSource correction;
	boolean resetCorrection;
	
	double integralDisableDistance = 5;

	double accuracyFinishCount = 0;
	double currentCount = 0;
	
    public DriveAnglePIDCommand(double angle, AngleUnit unit) {
    	this(angle, new AngleGyroCorrectionSource(unit), true);    	
    }

    public DriveAnglePIDCommand(double angle, AngleGyroCorrectionSource correction, boolean resetCorrection) {
    	//angle = angle - (0.2768*angle - 3.1668) * Math.signum(angle);
    	this.angle = angle;
    	this.correction = correction;
    	this.resetCorrection = resetCorrection;
    	requires(Drive.getInstance());
	}

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void onExecute() {
    	SmartDashboard.putNumber("Drive Angle PID error", pid.getError());
    	SmartDashboard.putNumber("Drive Angle PID output", pid.get());
    	SmartDashboard.putNumber("Drive Angle PID total error", pid.getTotalError());
		if(Math.abs(pid.getError()) > integralDisableDistance) {
			pid.setPID(RobotMap.TURN_P, 0, RobotMap.TURN_D);
			pid.resetTotalError();
		} else {
			pid.setPID(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D);
		}
		
		if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
			pid.resetTotalError();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
    	if(Math.abs(pid.getError()) < RobotMap.TURN_THRESHOLD) {
    		currentCount++;
    		if(currentCount > 3)
    			pid.resetTotalError();

    	} else {
    		currentCount = 0;
    	}
    	return currentCount > accuracyFinishCount;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		if(!interrupted) {
			correction.reset();
			new DriveSimpleDistanceWithGyroCommand(1, 0.2, correction).start();
		}
		pid.disable();

	}

	@Override
	protected void onStart() {
		Drive.getInstance().setPIDEnabled(true);
		controller = new AngleController();
		correction.reset();
		pid = new PID(RobotMap.TURN_P,RobotMap.TURN_I,RobotMap.TURN_D, correction, controller);
		pid.setOutputRange(0.1, 0.3);
		pid.enable();
    	pid.setSetpoint(angle);
	}
}

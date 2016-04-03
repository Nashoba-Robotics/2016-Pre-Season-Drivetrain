package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDCommand extends NRCommand {

	double totalError = 0;
	
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
    	this.angle = angle;
    	this.correction = correction;
    	this.resetCorrection = resetCorrection;
    	requires(Drive.getInstance());
	}

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void onExecute() {
    	double output = 0;
    	
    	final double TURN_P = SmartDashboard.getNumber("Turn P");
    	final double TURN_I = SmartDashboard.getNumber("Turn I");

		if(Math.abs(correction.pidGet()-angle) > integralDisableDistance) {
			totalError = 0;
		} else {
			totalError += TURN_I*(correction.pidGet()-angle);
		}
		
		if(Math.signum(correction.pidGet()-angle) != Math.signum(totalError)) {
			totalError = 0;
		}
		
		output += TURN_P*(correction.pidGet()-angle);

		output += totalError;
		if(Math.abs(output) < 0.1) {
			output = 0.1 * Math.signum(output);
		} else if(Math.abs(output) > 0.3) {
			output = 0.3 * Math.signum(output);
		}
		
		output *= -1;
		controller.pidWrite(output);
		
    	SmartDashboard.putNumber("Drive Turn Error", correction.pidGet()-angle);
    	SmartDashboard.putNumber("Drive Turn Total Error", totalError);
    	SmartDashboard.putNumber("Drive Turn Output", output);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
    	if(Math.abs(correction.pidGet()-angle) < RobotMap.TURN_THRESHOLD) {
    		currentCount++;
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
		totalError = 0;
	}

	@Override
	protected void onStart() {
		Drive.getInstance().setPIDEnabled(true);
		controller = new AngleController();
		correction.reset();
	}
}

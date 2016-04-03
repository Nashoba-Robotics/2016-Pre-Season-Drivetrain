package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.lib.network.AndroidServer;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAngleJetsonPIDCommand extends NRCommand {
	double totalError = 0;
	
	double angle;
		
	AngleGyroCorrectionSource correction;
	AngleController controller;
	boolean resetCorrection;
	
	double integralDisableDistance = 5;

	double accuracyFinishCount = 0;
	double currentCount = 0;
	
	boolean goodToGo = true;
	
    public DriveAngleJetsonPIDCommand() {
    	requires(Drive.getInstance());
		correction = new AngleGyroCorrectionSource(AngleUnit.DEGREE);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void onExecute() {
    	double output = 0;
		if(Math.abs(correction.pidGet()-angle) > integralDisableDistance) {
			totalError = 0;
		} else {
			totalError += RobotMap.TURN_I*(correction.pidGet()-angle);
		}
		
		if(Math.signum(correction.pidGet()-angle) != Math.signum(totalError)) {
			totalError = 0;
		}
		
		output += RobotMap.TURN_P*(correction.pidGet()-angle);

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
    	if(!goodToGo)
    		return true;
    	if(Math.abs(correction.pidGet()-angle) < RobotMap.TURN_THRESHOLD) {
    		currentCount++;
    	} else {
    		currentCount = 0;
    	}
    	return currentCount > accuracyFinishCount;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		if(!interrupted && goodToGo) {
			correction.reset();
			new DriveSimpleDistanceWithGyroCommand(1, 0.2, correction).start();
		}
		totalError = 0;
		goodToGo = true;
	}

	@Override
	protected void onStart() {
    	if(!AndroidServer.getInstance().goodToGo()) { 
    		System.out.println("Android connection not good to go");
    		goodToGo = false;
    		return;
    	}
		
		angle = AndroidServer.getInstance().getTurnAngle();
		System.out.println("Drive angle Jetson PID start - angle = " + angle);

		if(Math.abs(angle) < RobotMap.TURN_THRESHOLD) {
			goodToGo = false;
		}
		
		Drive.getInstance().setPIDEnabled(true);
		
		controller = new AngleController();
		correction.reset();
	}
}

package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.commandgroups.AlignCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAngleJetsonPIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	AngleGyroCorrectionSource correction;
	boolean resetCorrection;

	
    public DriveAngleJetsonPIDCommand() {
    	requires(Drive.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	SmartDashboard.putData("Jetson Angle PID", pid);
    	SmartDashboard.putNumber("Jetson Angle PID Error", pid.getError());
		if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
			System.out.println("Jetson PID error resetting");
			pid.resetTotalError();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(pid.getError()) < 2;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		System.out.println("Drive angle Jetson PID finish interrupted? " + interrupted);

		pid.disable();
	}

	@Override
	protected void onStart() {
		System.out.println("Drive angle Jetson PID start");
		angle = -UDPServer.getInstance().getTurnAngle();
		/*if(Math.abs(angle) < .5) {
			angle = 0;
		} else {
			angle = angle - (0.2768*angle - 3.1668) * Math.signum(angle);
		}*/
		correction = new AngleGyroCorrectionSource(AngleUnit.DEGREE);
		pid = new PID(0.0007, 0.0001, 0.0001, new AngleGyroCorrectionSource(), new AngleController());
		System.out.println("I'm starting to turn");
    	pid.enable();
    	pid.setSetpoint(angle);
	}
}

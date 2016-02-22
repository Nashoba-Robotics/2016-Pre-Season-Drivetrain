package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.lib.network.UDPServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAngleJetsonPIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	
    public DriveAngleJetsonPIDCommand() {
    	requires(Drive.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	SmartDashboard.putData("Jetson Angle PID", pid);
    	SmartDashboard.putNumber("Jetson Angle PID Error", pid.getError());
    	pid.setPID(pid.getError()*0.0007, pid.getI(), pid.getD());
		if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
			pid.resetTotalError();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(pid.getError()) < 0.5;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		pid.disable();
	}

	@Override
	protected void onStart() {
		angle = UDPServer.getInstance().getTurnAngle();
		pid = new PID(angle*0.0007, 0.0005, 0.0001, new AngleGyroCorrectionSource(), new AngleController());
    	pid.enable();
    	pid.setSetpoint(angle);
	}
}

package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	AngleGyroCorrectionSource correction;
	boolean resetCorrection;
	
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
    protected void onExecute() {
    	SmartDashboard.putData("Angle PID", pid);
    	SmartDashboard.putNumber("Angle PID Error", pid.getError());
    	pid.setPID(pid.getError()*0.0007, pid.getI(), pid.getD());
		if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
			pid.resetTotalError();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(pid.getError()) < 0.25;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		pid.disable();
	}

	@Override
	protected void onStart() {
		pid = new PID(angle*0.0007, 0.0005, 0.0001, new AngleGyroCorrectionSource(), new AngleController());
		pid.enable();
		if(resetCorrection) {
			correction.reset();
		}
    	pid.setSetpoint(angle);
	}
}

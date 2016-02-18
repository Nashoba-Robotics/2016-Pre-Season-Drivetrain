package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrection;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;

/**
 *
 */
public class DriveTurnCommand extends NRCommand {

	private AngleGyroCorrection gyro;
	double angle;
	
    public DriveTurnCommand(double angle, AngleUnit unit) {
    	if(unit == AngleUnit.RADIAN) {
    		angle = Math.toDegrees(angle);
    	}
    	this.angle = angle;
        requires(Drive.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro = new AngleGyroCorrection(angle, AngleUnit.DEGREE);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Drive.getInstance().arcadeDrive(0,gyro.getTurnValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return gyro.getAngleErrorDegrees() > 0;
    }
}

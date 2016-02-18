package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrection;
import edu.nr.lib.AngleUnit;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnCommand extends Command {

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
    protected void execute() {
    	Drive.getInstance().arcadeDrive(0,gyro.getTurnValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return gyro.getAngleErrorDegrees() > 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

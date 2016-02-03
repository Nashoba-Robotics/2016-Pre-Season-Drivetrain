package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrection;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAnglePIDCommand extends Command {

	PIDController pid;
	
	double angle;
	
    public DriveAnglePIDCommand(double angle) {
    	this.angle = angle; 
    	requires(Drive.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {//Something strange is causing the number to double in the gyro correction
    	pid = new PIDController(angle*0.0015, 0.002, 0, new AngleGyroCorrection(angle/2), new AngleController());
    	pid.enable();
    	pid.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putData("Angle PID", pid);
    	SmartDashboard.putNumber("Angle PID Error", pid.getError());
    	pid.setPID(pid.getError()*0.0015, pid.getI(), pid.getD());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(pid.getError()) < 0.25;
    }

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	pid.disable();
    }
}

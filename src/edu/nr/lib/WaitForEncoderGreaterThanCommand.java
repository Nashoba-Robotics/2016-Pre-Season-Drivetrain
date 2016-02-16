package edu.nr.lib;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitForEncoderGreaterThanCommand extends Command {

	TalonEncoder enc;
	double value;
	
    public WaitForEncoderGreaterThanCommand(TalonEncoder enc, double value) {
    	this.enc = enc;
    	this.value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return enc.get() > value;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

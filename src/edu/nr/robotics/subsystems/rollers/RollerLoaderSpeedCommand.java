package edu.nr.robotics.subsystems.rollers;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollerLoaderSpeedCommand extends Command {

	double val;
	
    public RollerLoaderSpeedCommand(double val) {
    	this.val = val;
    	requires(Rollers.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Rollers.getInstance().setLoaderSetpoint(val);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

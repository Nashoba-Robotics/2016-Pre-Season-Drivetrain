package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ResetEncodersCommand extends NRCommand {

	public ResetEncodersCommand() {
		requires(Drive.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Drive.getInstance().resetEncoders();
	}
}

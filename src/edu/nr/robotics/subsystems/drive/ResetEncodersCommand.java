package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;

/**
 *
 */
public class ResetEncodersCommand extends CMD {

	public ResetEncodersCommand() {
		requires(Drive.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Drive.getInstance().resetEncoders();
	}
}

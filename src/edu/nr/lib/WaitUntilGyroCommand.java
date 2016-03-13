package edu.nr.lib;

import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.drive.FieldCentric;

/**
 *
 */
public class WaitUntilGyroCommand extends NRCommand {

	AngleGyroCorrection gyroCorrection;
	double angle;

	public WaitUntilGyroCommand(double angle) {
		this.angle = angle;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinishedNR() {
		return gyroCorrection.get() > angle;
	}

	@Override
	protected void onStart() {
        gyroCorrection = new AngleGyroCorrection();
	}
}

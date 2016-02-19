package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.nr.lib.AngleGyroCorrection;
import edu.nr.lib.FieldCentric;

/**
 *
 */
public class DriveSimpleDistanceWithGyroCommand extends NRCommand {

	double distance; // in meters
	double speed; // from 0 to 1
	AngleGyroCorrection gyroCorrection;

	public DriveSimpleDistanceWithGyroCommand(double distance, double speed) {
		this.speed = speed;
		this.distance = distance;
		requires(Drive.getInstance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return FieldCentric.getInstance().getDistance() > distance;
	}

	@Override
	protected void onStart() {
        gyroCorrection = new AngleGyroCorrection();
		FieldCentric.getInstance().reset();
	}

	@Override
	protected void onExecute() {
		Drive.getInstance().arcadeDrive(speed, gyroCorrection.getTurnValue());
	}

	@Override
	protected void onEnd(boolean interrupted) {
		Drive.getInstance().tankDrive(0, 0);
	}
}

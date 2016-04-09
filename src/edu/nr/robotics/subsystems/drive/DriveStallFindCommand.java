package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStallFindCommand extends NRCommand {

	public double lastSpeed = 0;
	public double lastTime;
	
	public DriveStallFindCommand(double distance, double speed) {
		requires(Drive.getInstance());
	}
	
	@Override
	public void onStart() {
		lastTime = System.currentTimeMillis();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinishedNR() {
		return lastSpeed >= 1;
	}

	@Override
	protected void onExecute() {
		if(System.currentTimeMillis() - lastTime > 1000) {
			double speed = lastSpeed += 0.01;

			Drive.getInstance().arcadeDrive(0, speed);
			lastTime = System.currentTimeMillis();
			SmartDashboard.putNumber("Stall Find Speed", speed);
			lastSpeed = speed;
		} else {
			Drive.getInstance().arcadeDrive(0, lastSpeed);
		}

	}

	@Override
	protected void onEnd(boolean interrupted) {
		lastSpeed = 0;
	}
}

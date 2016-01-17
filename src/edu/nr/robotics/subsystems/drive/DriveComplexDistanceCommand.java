package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;
import edu.nr.lib.FieldCentric;
import edu.nr.lib.path.OneDimensionalPath;

/**
 *
 */
public class DriveComplexDistanceCommand extends CMD {

	OneDimensionalPath path; // from 0 to 1
	double Kv, Ka, Kp, Kd;
	double startingTime;
	double prevTime;
	double errorLast;

	public DriveComplexDistanceCommand(OneDimensionalPath path, double Kv, double Ka, double Kp, double Kd) {
		this.path = path;
		prevTime = 0;
		errorLast = 0;

		this.Kv = Kv;
		this.Ka = Ka;
		this.Kp = Kp;
		this.Kd = Kd;
		System.out.println("Complex Distance Drive Command Started");
		requires(Drive.getInstance());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return prevTime > path.getLength();
	}

	@Override
	protected void onStart() {
		System.out.println("Complex Distance Drive Command Start Mode");
		startingTime = (double) System.currentTimeMillis() / 1000;

		FieldCentric.getInstance().reset();
	}

	@Override
	protected void onExecute() {
		double time = (double) System.currentTimeMillis() / 1000 - startingTime;
		double dt = time - prevTime;
		prevTime = time;

		double error = path.getPosition(time) + FieldCentric.getInstance().getX();
		double errorDeriv = (error - errorLast) / dt;
		double motorSpeed = Kv * path.getSpeed(time) + Ka * path.getAcc(time) + Kp * error + Kd * errorDeriv;
		errorLast = error;
		Drive.getInstance().arcadeDrive(motorSpeed, 0);

	}

	@Override
	protected void onEnd(boolean interrupted) {
	}
}

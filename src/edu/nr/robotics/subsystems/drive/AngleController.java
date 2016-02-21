package edu.nr.robotics.subsystems.drive;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AngleController implements PIDOutput {

	public AngleController() {
	}

	@Override
	public void pidWrite(double output) {
		Drive.getInstance().tankDrive(output, -output);
		SmartDashboard.putNumber("Angle PID Output", output);
	}

}

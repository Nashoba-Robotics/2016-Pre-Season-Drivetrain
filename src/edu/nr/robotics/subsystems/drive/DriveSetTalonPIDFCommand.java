package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSetTalonPIDFCommand extends NRCommand {	
	
	@Override
	protected void onStart() {
		Drive.getInstance().setPID(
				SmartDashboard.getNumber("Talon P"),
				SmartDashboard.getNumber("Talon I"),
				SmartDashboard.getNumber("Talon D"),
				SmartDashboard.getNumber("Talon F")
				);
	}
	
}

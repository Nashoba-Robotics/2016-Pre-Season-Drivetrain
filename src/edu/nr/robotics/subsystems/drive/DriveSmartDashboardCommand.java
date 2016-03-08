package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSmartDashboardCommand extends NRCommand {
    
	double val;

	
    public  DriveSmartDashboardCommand() {
        requires(Drive.getInstance());
    }

	@Override
	protected void onStart() {
		Drive.getInstance().setPID(SmartDashboard.getNumber("Drive P"), 0, 0, 1);
		Drive.getInstance().setPIDEnabled(true);
		val = SmartDashboard.getNumber("TurnSpeed");
	}

	@Override
	protected void onExecute() {
		Drive.getInstance().tankDrive(val, -val);
	}

	@Override
	protected void onEnd(boolean interrupted) {
		Drive.getInstance().tankDrive(0, 0);
	}

	@Override
	protected boolean isFinishedNR() {
		return false;
	}
}

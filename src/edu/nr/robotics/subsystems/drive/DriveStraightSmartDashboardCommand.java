package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightSmartDashboardCommand extends NRCommand {

	boolean PID;	
	
    public DriveStraightSmartDashboardCommand() {
        requires(Drive.getInstance());
    }

	@Override
	protected void onStart() {
		Drive.getInstance().setMotorSpeed(SmartDashboard.getNumber("Drive Turn Constant"), SmartDashboard.getNumber("Drive Turn Constant"));
	}
	
	@Override
	protected boolean isFinishedNR() {
		return false;
	}
    
}

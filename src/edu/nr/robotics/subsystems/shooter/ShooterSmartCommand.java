package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.Robot;

public class ShooterSmartCommand extends NRCommand {

	@Override
	public void onStart() {
		Robot.getInstance().useDumbShooter = false;
	}
	
}

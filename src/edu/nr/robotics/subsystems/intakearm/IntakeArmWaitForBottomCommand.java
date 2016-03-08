package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

public class IntakeArmWaitForBottomCommand extends NRCommand {

	public boolean isFinishedNR() {
		return IntakeArm.getInstance().isBotLimitSwitchClosed();
	}

}

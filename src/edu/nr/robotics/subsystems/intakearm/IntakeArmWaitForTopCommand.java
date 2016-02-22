package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

public class IntakeArmWaitForTopCommand extends NRCommand {

	public boolean isFinished() {
		return IntakeArm.getInstance().isTopLimitSwitchClosed();
	}

}

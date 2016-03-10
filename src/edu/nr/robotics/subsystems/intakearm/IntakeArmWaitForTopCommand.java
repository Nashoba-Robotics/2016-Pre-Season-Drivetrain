package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

public class IntakeArmWaitForTopCommand extends NRCommand {

	@Override
	public boolean isFinishedNR() {
		return IntakeArm.getInstance().isTopLimitSwitchClosed();
	}

}

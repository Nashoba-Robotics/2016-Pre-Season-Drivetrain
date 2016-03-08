package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;

public class HoodWaitForBottomCommand extends NRCommand {
	
	public HoodWaitForBottomCommand() {
		requires(Hood.getInstance());
	}
	
	public boolean isFinishedNR() {
		return Hood.getInstance().isBotLimitSwitchClosed();
	}

}

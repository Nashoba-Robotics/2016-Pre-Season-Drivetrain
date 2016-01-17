package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.EmptyCommand;

public class HoodNeutralCommand extends EmptyCommand {

	public HoodNeutralCommand() {
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		Hood.getInstance().disable();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

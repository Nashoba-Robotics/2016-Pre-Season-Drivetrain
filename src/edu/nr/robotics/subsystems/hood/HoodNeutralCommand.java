package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.CMD;

public class HoodNeutralCommand extends CMD {

	public HoodNeutralCommand() {
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		Hood.getInstance().disable();
	}
}

package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.CMD;

public class IntakeArmOffCommand extends CMD {

	public IntakeArmOffCommand() {
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().disable();
	}

}

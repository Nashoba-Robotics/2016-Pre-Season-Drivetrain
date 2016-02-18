package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.CMD;

public class IntakeArmOnCommand extends CMD {

	public IntakeArmOnCommand() {
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().enable();
	}

}

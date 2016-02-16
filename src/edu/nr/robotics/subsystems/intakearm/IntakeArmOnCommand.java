package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.EmptyCommand;

public class IntakeArmOnCommand extends EmptyCommand {

	public IntakeArmOnCommand() {
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().enable();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

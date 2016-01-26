package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.EmptyCommand;

public class IntakeArmNeutralCommand extends EmptyCommand {

	public IntakeArmNeutralCommand() {
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().disable();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

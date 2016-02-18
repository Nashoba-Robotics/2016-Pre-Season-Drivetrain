package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

public class IntakeArmOnCommand extends NRCommand {

	public IntakeArmOnCommand() {
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().enable();
	}

}

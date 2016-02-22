package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

public class IntakeArmPositionCommand extends NRCommand {

	double val;
	
	public IntakeArmPositionCommand(double val) {
		this.val = val;
		requires(IntakeArm.getInstance());
	}
	
	@Override
	protected void onStart() {
		IntakeArm.getInstance().enable();
	}

	@Override
	protected void onExecute() {
		IntakeArm.getInstance().setSetpoint(val);
	}
}

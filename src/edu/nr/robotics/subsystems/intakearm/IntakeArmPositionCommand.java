package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.CMD;

public class IntakeArmPositionCommand extends CMD {

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

	@Override
	protected void onEnd(boolean interrupted) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}

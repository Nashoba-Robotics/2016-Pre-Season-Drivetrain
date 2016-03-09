package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.RobotMap;

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
	
	@Override
	protected boolean isFinishedNR() {
		System.err.println("val " + ((IntakeArm.getInstance().get() - val) < RobotMap.INTAKE_ARM_THRESHOLD));
		return Math.abs(IntakeArm.getInstance().get() - val) < RobotMap.INTAKE_ARM_THRESHOLD;
	}
}

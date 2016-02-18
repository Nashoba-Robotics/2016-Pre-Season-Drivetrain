package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;

public class HoodPositionCommand extends NRCommand {

	double val;
	
	public HoodPositionCommand(double val) {
		this.val = val;
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		Hood.getInstance().enable();
	}

	@Override
	protected void onExecute() {
		Hood.getInstance().setSetpoint(val);
	}

	@Override
	protected void onEnd(boolean interrupted) {

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

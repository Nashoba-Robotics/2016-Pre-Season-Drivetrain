package edu.nr.robotics.subsystems.elevator;

import edu.nr.lib.CMD;

public class ElevatorOnCommand extends CMD {

	double val;
	
	ElevatorOnCommand(double val) {
		this.val = val;
		requires(Elevator.getInstance());
	}
	
	@Override
	protected void onStart() {
		Elevator.getInstance().setMotorValue(val);
	}

	@Override
	protected void onExecute() {
		
	}

	@Override
	protected void onEnd(boolean interrupted) {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

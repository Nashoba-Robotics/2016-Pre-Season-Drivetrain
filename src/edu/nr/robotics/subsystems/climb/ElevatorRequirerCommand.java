package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;

public class ElevatorRequirerCommand extends NRCommand {

	public ElevatorRequirerCommand() {
		requires(Elevator.getInstance());
	}
	
	public boolean isFinished() {
		return false;
	}
	
}

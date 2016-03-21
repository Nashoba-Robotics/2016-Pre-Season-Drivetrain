package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ElevatorUnlatchCommand extends CommandGroup {
	
	public ElevatorUnlatchCommand() {
		addParallel(new ElevatorDownCommand());
		addSequential(new ElevatorWaitUntilChangedByCommand(RobotMap.ELEVATOR_UNLATCH_DISTANCE));
	}
}

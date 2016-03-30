package edu.nr.robotics.subsystems.loaderroller;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaserCannonTriggerCommand extends CommandGroup {

	public LaserCannonTriggerCommand() {
		addParallel(new LoaderRollerIntakeCommand());
		addSequential(new WaitCommand(3));
		addSequential(new LoaderRollerNeutralCommand());
	}

}

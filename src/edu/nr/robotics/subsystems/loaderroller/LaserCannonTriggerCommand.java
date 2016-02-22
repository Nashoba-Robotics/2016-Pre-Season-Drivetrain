package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.robotics.subsystems.lights.LightsOffCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaserCannonTriggerCommand extends CommandGroup {

	public LaserCannonTriggerCommand() {
		addSequential(new LoaderRollerIntakeCommand());
		addSequential(new WaitCommand(3));
		addSequential(new LoaderRollerNeutralCommand());
		addSequential(new LightsOffCommand());
	}

}

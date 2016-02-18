package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.robotics.subsystems.lights.LightsOffCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaserCannonTriggerCommand extends CommandGroup {

	public LaserCannonTriggerCommand() {
		addSequential(new LoaderRollerForwardCommand());
		addSequential(new WaitCommand(1));
		addSequential(new LoaderRollerNeutralCommand());
		addSequential(new LightsOffCommand());
	}

}

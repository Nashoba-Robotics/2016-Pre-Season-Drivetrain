package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ClimbUnlatchCommand extends CommandGroup {

    public ClimbUnlatchCommand() {
        addParallel(new ClimbDownCommand());
        addSequential(new WaitCommand(0.1));
        addSequential(new ClimbOffCommand());
    }
}

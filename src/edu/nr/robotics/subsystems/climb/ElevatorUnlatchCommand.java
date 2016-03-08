package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ElevatorUnlatchCommand extends CommandGroup {

    public ElevatorUnlatchCommand() {
        addParallel(new ElevatorDownCommand());
        addParallel(new ElevatorRequirerCommand());
        addSequential(new WaitCommand(0.1));
        addSequential(new ElevatorOffCommand());
    }
}

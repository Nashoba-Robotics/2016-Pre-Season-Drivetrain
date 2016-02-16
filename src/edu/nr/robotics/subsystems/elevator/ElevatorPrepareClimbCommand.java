package edu.nr.robotics.subsystems.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ElevatorPrepareClimbCommand extends CommandGroup {

    public ElevatorPrepareClimbCommand() {
        addParallel(new ElevatorDownCommand());
        addSequential(new WaitCommand(0.1));
        addSequential(new ElevatorOffCommand());
    }
}

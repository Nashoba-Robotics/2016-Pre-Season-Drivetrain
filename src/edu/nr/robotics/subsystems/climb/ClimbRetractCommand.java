package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbRetractCommand extends CommandGroup {

    public ClimbRetractCommand() {
        addParallel(new ClimbDownCommand());
        addSequential(new ClimbWaitForMotorStallTimeCommand(1));
    }
}

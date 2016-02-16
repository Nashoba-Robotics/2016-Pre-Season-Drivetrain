package edu.nr.robotics.subsystems.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorClimbCommand extends CommandGroup {

    public ElevatorClimbCommand() {
        addParallel(new ElevatorDownCommand());
        addSequential(new ElevatorWaitForMotorStallTimeCommand(1));
    }
}

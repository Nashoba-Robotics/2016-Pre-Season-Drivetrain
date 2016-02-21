package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorUpCommand extends CommandGroup {
    
    public  ElevatorUpCommand() {
        addSequential(new ElevatorSpeedCommand(1));
    }
}

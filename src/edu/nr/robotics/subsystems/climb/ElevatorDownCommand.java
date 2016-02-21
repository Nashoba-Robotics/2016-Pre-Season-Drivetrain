package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorDownCommand extends CommandGroup {
    
    public  ElevatorDownCommand() {
        addSequential(new ElevatorSpeedCommand(-1));
    }
}

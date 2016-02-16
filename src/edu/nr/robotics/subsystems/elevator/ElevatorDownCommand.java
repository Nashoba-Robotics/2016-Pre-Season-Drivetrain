package edu.nr.robotics.subsystems.elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorDownCommand extends CommandGroup {
    
    public  ElevatorDownCommand() {
        addSequential(new ElevatorSpeedCommand(-1));
    }
}

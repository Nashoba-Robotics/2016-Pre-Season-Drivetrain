package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorOffCommand extends CommandGroup {
    
    public  ElevatorOffCommand() {
        addSequential(new ElevatorSpeedCommand(0));
    }
}

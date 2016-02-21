package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbOffCommand extends CommandGroup {
    
    public  ClimbOffCommand() {
        addSequential(new ClimbSpeedCommand(0));
    }
}

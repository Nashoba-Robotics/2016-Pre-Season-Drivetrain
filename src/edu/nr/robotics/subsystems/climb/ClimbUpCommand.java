package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbUpCommand extends CommandGroup {
    
    public  ClimbUpCommand() {
        addSequential(new ClimbSpeedCommand(1));
    }
}

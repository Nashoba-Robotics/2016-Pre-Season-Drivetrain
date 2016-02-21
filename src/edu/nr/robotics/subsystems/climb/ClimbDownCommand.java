package edu.nr.robotics.subsystems.climb;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbDownCommand extends CommandGroup {
    
    public  ClimbDownCommand() {
        addSequential(new ClimbSpeedCommand(-1));
    }
}

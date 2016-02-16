package edu.nr.robotics.subsystems.loaderroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoaderRollerReverseCommand extends CommandGroup {
    
    public  LoaderRollerReverseCommand() {
        addSequential(new LoaderRollerSpeedCommand(-1));
    }
}

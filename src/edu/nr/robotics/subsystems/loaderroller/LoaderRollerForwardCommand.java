package edu.nr.robotics.subsystems.loaderroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoaderRollerForwardCommand extends CommandGroup {
    
    public  LoaderRollerForwardCommand() {
        addSequential(new LoaderRollerSpeedCommand(1));
    }
}

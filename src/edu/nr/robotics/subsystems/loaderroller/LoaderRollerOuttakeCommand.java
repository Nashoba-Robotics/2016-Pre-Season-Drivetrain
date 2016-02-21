package edu.nr.robotics.subsystems.loaderroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoaderRollerOuttakeCommand extends CommandGroup {
    
    public  LoaderRollerOuttakeCommand() {
        addSequential(new LoaderRollerSpeedCommand(-1));
    }
    
    @Override
    public void end() {
    	new LoaderRollerSpeedCommand(0);
    }
}

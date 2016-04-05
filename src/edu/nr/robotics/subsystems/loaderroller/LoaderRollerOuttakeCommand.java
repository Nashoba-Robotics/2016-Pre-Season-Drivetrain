package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoaderRollerOuttakeCommand extends CommandGroup {
    
    public  LoaderRollerOuttakeCommand() {
        addSequential(new LoaderRollerSpeedCommand(RobotMap.LOADER_OUTTAKE_SPEED));
    }
}

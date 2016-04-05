package edu.nr.robotics.subsystems.shooter;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterOffCommand extends CommandGroup {
    
    public  ShooterOffCommand() {
        addSequential(new ShooterSpeedCommand(0));
    }
}

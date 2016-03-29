package edu.nr.robotics.subsystems.shooter;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterHighCommand extends CommandGroup {
    
    public  ShooterHighCommand() {
        addSequential(new ShooterSpeedCommand(RobotMap.SHOOTER_FAST_SPEED));
    }
}

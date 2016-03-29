package edu.nr.robotics.subsystems.shooter;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterLowCommand extends CommandGroup {
    
    public  ShooterLowCommand() {
        addSequential(new ShooterSpeedCommand(RobotMap.SHOOTER_SLOW_SPEED));
    }
}

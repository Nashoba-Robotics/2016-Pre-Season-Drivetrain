package edu.nr.robotics.subsystems.shooter;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterManualHighCommand extends CommandGroup {
    
    public  ShooterManualHighCommand() {
        addSequential(new ShooterOnManualCommand(RobotMap.SHOOTER_FAST_SPEED));
    }
}

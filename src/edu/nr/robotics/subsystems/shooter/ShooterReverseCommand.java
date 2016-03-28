package edu.nr.robotics.subsystems.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterReverseCommand extends CommandGroup {
    
    public  ShooterReverseCommand() {
        addSequential(new ShooterSpeedCommand(-1));
    }
}

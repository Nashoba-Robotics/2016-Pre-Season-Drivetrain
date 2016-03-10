package edu.nr.robotics.commandgroups;

import edu.nr.robotics.auton.AutonAlignCommand;
import edu.nr.robotics.subsystems.loaderroller.LaserCannonTriggerCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignAndShootCommandGroup extends CommandGroup {
    
    public  AlignAndShootCommandGroup() {
        addSequential(new AutonAlignCommand());
    	addSequential(new LaserCannonTriggerCommand());
    }
    
    @Override
	public void end() {
    	Shooter.getInstance().setSetpoint(0);
    }
}

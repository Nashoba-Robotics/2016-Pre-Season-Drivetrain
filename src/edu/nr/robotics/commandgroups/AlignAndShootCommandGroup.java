package edu.nr.robotics.commandgroups;

import edu.nr.robotics.auton.AutonAlignCommand;
import edu.nr.robotics.subsystems.loaderroller.LaserCannonTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignAndShootCommandGroup extends CommandGroup {
    
    public  AlignAndShootCommandGroup() {
        addSequential(new AutonAlignCommand());
    	addSequential(new LaserCannonTriggerCommand());
    }
}

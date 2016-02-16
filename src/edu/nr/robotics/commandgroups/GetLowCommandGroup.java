package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.hood.HoodBottomCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetLowCommandGroup extends CommandGroup {
    
    public  GetLowCommandGroup() {
        addParallel(new HoodBottomCommand());
        addParallel(new IntakeArmBottomHeightCommand());
    }
}

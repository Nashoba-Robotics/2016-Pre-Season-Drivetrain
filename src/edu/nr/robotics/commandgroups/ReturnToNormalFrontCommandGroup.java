package edu.nr.robotics.commandgroups;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReturnToNormalFrontCommandGroup extends CommandGroup {
    
	
	/**
	 * Only works in autonomous because of the gyroscope method
	 */
    public  ReturnToNormalFrontCommandGroup() {
        AngleGyroCorrectionSource correction = new AngleGyroCorrectionSource();
        addSequential(new DriveAnglePIDCommand(0.0, correction, false));
    }
}

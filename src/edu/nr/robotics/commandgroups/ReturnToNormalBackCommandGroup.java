package edu.nr.robotics.commandgroups;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReturnToNormalBackCommandGroup extends CommandGroup {
    
	/**
	 * Only works in autonomous because of the gyroscope method
	 */
    public  ReturnToNormalBackCommandGroup() {
        AngleGyroCorrectionSource correction = new AngleGyroCorrectionSource(180, AngleUnit.DEGREE);
        addSequential(new DriveAnglePIDCommand(0.0, correction, false));
    }
}

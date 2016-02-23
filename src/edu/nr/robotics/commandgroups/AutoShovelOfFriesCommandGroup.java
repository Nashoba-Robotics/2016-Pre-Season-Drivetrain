package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmSetMaxSpeedCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmWaitForBottomCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShovelOfFriesCommandGroup extends CommandGroup {

	public static final double distance = 6;
	public static final double speed = 1;
	
	//TODO: Confirm the behaviour of auto shovel of fries

    public  AutoShovelOfFriesCommandGroup() {
        addSequential(new IntakeArmBottomHeightCommandGroup());
        addSequential(new IntakeArmWaitForBottomCommand());
        addSequential(new IntakeArmSetMaxSpeedCommand(1));
        addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new DriveSimpleDistanceCommand(distance,speed));
        addSequential(new IntakeArmSetMaxSpeedCommand(1));
    }
}

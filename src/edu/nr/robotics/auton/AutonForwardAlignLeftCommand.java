package edu.nr.robotics.auton;

import edu.nr.lib.AngleUnit;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonForwardAlignLeftCommand extends CommandGroup {

	public AutonForwardAlignLeftCommand() {
		addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS, 0.05));
		addSequential(new DriveDistanceCommand(14, 0.6));
		addSequential(new DriveAnglePIDCommand(20, AngleUnit.DEGREE));
		addSequential(new AutonAlignCommand());
	}
	
}

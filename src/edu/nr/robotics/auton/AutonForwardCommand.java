package edu.nr.robotics.auton;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.hood.HoodMoveDownUntilLimitSwitchCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonForwardCommand extends CommandGroup {

	public AutonForwardCommand() {
    	addSequential(new HoodMoveDownUntilLimitSwitchCommand());
		addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS, 0.05));
		addSequential(new DriveDistanceCommand(14, 0.6));
	}
	
}

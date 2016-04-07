package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmBottomHeightCommand extends CommandGroup {

    public IntakeArmBottomHeightCommand() {
    	addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_BOTTOM_POS));
    }
}
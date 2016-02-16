package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerForwardCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmIntakeHeightCommand extends CommandGroup {

    public IntakeArmIntakeHeightCommand() {
        addParallel(new IntakeRollerForwardCommand());
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_ARM_INTAKE_HEIGHT));
    }
}
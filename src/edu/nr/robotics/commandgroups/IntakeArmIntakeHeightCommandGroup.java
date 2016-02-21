package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerOuttakeCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmIntakeHeightCommandGroup extends CommandGroup {

    public IntakeArmIntakeHeightCommandGroup() {
        addParallel(new IntakeRollerOuttakeCommand());
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS));
    }
}
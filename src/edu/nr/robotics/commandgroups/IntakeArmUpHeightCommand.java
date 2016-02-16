package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmUpHeightCommand extends CommandGroup {

    public IntakeArmUpHeightCommand() {
        addParallel(new IntakeRollerNeutralCommand());
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_ARM_UP_HEIGHT));
    }
}

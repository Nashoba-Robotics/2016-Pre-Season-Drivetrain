package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerOuttakeCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerOuttakeCommand;
import edu.nr.robotics.subsystems.shooter.ShooterReverseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LowGoalStartCommandGroup extends CommandGroup {
    	
    public  LowGoalStartCommandGroup() {
    	addParallel(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS));
        addParallel(new IntakeRollerOuttakeCommand());
        addParallel(new LoaderRollerOuttakeCommand());
    }
}

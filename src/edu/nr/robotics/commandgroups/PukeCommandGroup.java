package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerReverseCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerReverseCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.nr.robotics.subsystems.shooter.ShooterOffCommand;
import edu.nr.robotics.subsystems.shooter.ShooterReverseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PukeCommandGroup extends CommandGroup {
    
    public  PukeCommandGroup() {
    	double oldRampRate = Shooter.getInstance().getRampRate();
    	Shooter.getInstance().setRampRate(RobotMap.SHOOTER_RAMP_RATE);
        addParallel(new ShooterReverseCommand());
        addParallel(new LoaderRollerReverseCommand());
        addParallel(new IntakeRollerReverseCommand());
        addSequential(new WaitCommand(1.5));
        addParallel(new ShooterOffCommand());
        addParallel(new LoaderRollerNeutralCommand());
        addParallel(new IntakeRollerNeutralCommand());
        Shooter.getInstance().setRampRate(oldRampRate);
    }
}

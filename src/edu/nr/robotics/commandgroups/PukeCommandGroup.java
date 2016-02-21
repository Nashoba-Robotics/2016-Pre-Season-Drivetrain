package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerIntakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.nr.robotics.subsystems.shooter.ShooterOffCommand;
import edu.nr.robotics.subsystems.shooter.ShooterReverseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class PukeCommandGroup extends CommandGroup {
    
	double oldRampRate;
	
    public  PukeCommandGroup() {
        addParallel(new ShooterReverseCommand());
        addParallel(new LoaderRollerIntakeCommand());
        addParallel(new IntakeRollerIntakeCommand());
        addSequential(new WaitCommand(1.5));
        addParallel(new ShooterOffCommand());
        addParallel(new LoaderRollerNeutralCommand());
        addParallel(new IntakeRollerNeutralCommand());
    }
    
    @Override
    public void start() {
    	oldRampRate = Shooter.getInstance().getRampRate();
    	Shooter.getInstance().setRampRate(RobotMap.SHOOTER_RAMP_RATE);
    }
    
    @Override
    public void end() {
        Shooter.getInstance().setRampRate(oldRampRate);
    }
}

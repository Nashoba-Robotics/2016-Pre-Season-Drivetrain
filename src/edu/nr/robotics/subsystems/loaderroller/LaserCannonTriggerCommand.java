package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LaserCannonTriggerCommand extends CommandGroup {

	public LaserCannonTriggerCommand() {
        addParallel(new LoaderRollerSpeedCommand(RobotMap.LOADER_SHOOT_SPEED));
		addSequential(new WaitCommand(3));
		addSequential(new LoaderRollerNeutralCommand());
	}
	
	@Override
	public void initialize() {
		System.out.println("Shooter button pressed." 
				+ " Photo 1: " + LoaderRoller.getInstance().hasIntakeBall() 
				+ " Photo 2: " + LoaderRoller.getInstance().hasLoaderBall() 
				+ " Photo 3: " + LoaderRoller.getInstance().hasShooterBall()
				+ " Shooter Speed: " + Shooter.getInstance().getScaledSpeed() 
				+ " Hood angle: " + Hood.getInstance().get());
	}

}

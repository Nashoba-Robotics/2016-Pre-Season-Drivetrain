package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;
import edu.nr.robotics.subsystems.shooter.Shooter;

public class LoaderRollerKeepAtPhotoCommand extends NRCommand {

	public LoaderRollerKeepAtPhotoCommand() {
		requires(LoaderRoller.getInstance());
	}
	
	@Override
	protected void onExecute() {
		
		if(LoaderRoller.getInstance().hasShooterBall()) {
			LoaderRoller.getInstance().setLoaderSpeed(RobotMap.LOADER_OUTTAKE_SPEED);
		} else if(!LoaderRoller.getInstance().hasLoaderBall() && LoaderRoller.getInstance().hasIntakeBall()) {
			LoaderRoller.getInstance().setLoaderSpeed(RobotMap.LOADER_INTAKE_SPEED);
		} else {
			LoaderRoller.getInstance().setLoaderSpeed(0);
		}
	}
	
	@Override
	protected boolean isFinishedNR() {
		return false;
	}
}

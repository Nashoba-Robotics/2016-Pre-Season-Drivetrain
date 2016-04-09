package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;

public class LoaderRollerIntakeUntilPhotoCommand extends NRCommand {

	public LoaderRollerIntakeUntilPhotoCommand() {
		requires(LoaderRoller.getInstance());
		requires(IntakeRoller.getInstance());
	}

	@Override
	protected void onStart() {
    	LoaderRoller.getInstance().setLoaderSpeed(RobotMap.LOADER_INTAKE_SPEED);
    	IntakeRoller.getInstance().setRollerSpeed(-1);
	}

	@Override
	protected boolean isFinishedNR() {
		return LoaderRoller.getInstance().hasIntakeBall();
	}
	
	@Override
	protected void onEnd() {
		LoaderRoller.getInstance().setLoaderSpeed(0);
		IntakeRoller.getInstance().setRollerSpeed(0);
	}

}

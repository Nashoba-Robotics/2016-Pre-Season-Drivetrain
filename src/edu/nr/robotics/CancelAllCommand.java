package edu.nr.robotics;

import edu.nr.robotics.subsystems.drive.Drive;
import edu.wpi.first.wpilibj.command.Command;

public class CancelAllCommand extends Command {

	public CancelAllCommand() {
		this.requires(Drive.getInstance());
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}

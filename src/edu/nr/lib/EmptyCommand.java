package edu.nr.lib;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class EmptyCommand extends CMD {
	public EmptyCommand(String name) {
		super(name);
	}

	public EmptyCommand(Subsystem requires) {
		requires(requires);
	}

	public EmptyCommand() {

	}

	@Override
	protected void onStart() {

	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void onExecute() {

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void onEnd(boolean interrupted) {
	}

}

package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;

public class DumbDriveOnCommand extends NRCommand {
	@Override
	public void onStart() {
		Drive.getInstance().setPIDEnabled(false);
	}
}

package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRCommand;

public class DumbDriveOffCommand extends NRCommand {
	@Override
	public void onStart() {
		Drive.getInstance().setPIDEnabled(true);
	}
}

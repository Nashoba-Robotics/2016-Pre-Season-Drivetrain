package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class IntakeArmResetPotentiometerCommand extends NRCommand {

	@Override
	protected void onStart() {
		IntakeArm.getInstance().reset();
	}
}

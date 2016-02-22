package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class LightsOnCommand extends NRCommand {

    public LightsOnCommand() {
        requires(Lights.getInstance());
    }

    @Override
    public void onStart() {
    	Lights.getInstance().enable();
    }
}

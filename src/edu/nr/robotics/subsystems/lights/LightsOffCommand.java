package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class LightsOffCommand extends NRCommand {

    public LightsOffCommand() {
        requires(Lights.getInstance());
    }

    @Override
    public void onStart() {
    	Lights.getInstance().disable();
    }
}

package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.EmptyCommand;

/**
 *
 */
public class LightsOffCommand extends EmptyCommand {

    public LightsOffCommand() {
        requires(Lights.getInstance());
    }

    @Override
    public void onStart() {
    	Lights.getInstance().disable();
    }
}

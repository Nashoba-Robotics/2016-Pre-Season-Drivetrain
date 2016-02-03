package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.EmptyCommand;

/**
 *
 */
public class LightsOnCommand extends EmptyCommand {

    public LightsOnCommand() {
        requires(Lights.getInstance());
    }

    @Override
    public void onStart() {
    	Lights.getInstance().enable();
    }
}

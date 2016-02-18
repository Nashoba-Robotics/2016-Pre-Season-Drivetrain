package edu.nr.lib;

/**
 *
 */
public class WaitForEncoderGreaterThanCommand extends NRCommand {

	TalonEncoder enc;
	double value;
	
    public WaitForEncoderGreaterThanCommand(TalonEncoder enc, double value) {
    	this.enc = enc;
    	this.value = value;
    }

    protected boolean isFinished() {
        return enc.get() > value;
    }
}

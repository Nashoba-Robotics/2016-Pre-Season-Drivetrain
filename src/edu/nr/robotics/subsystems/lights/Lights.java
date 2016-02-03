package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lights extends Subsystem implements SmartDashboardSource {
	
	Relay lights;
	static Lights singleton;
	
	private Lights() {
		lights = new Relay(RobotMap.LIGHTS_SPIKE);
		lights.setDirection(Relay.Direction.kForward);
		lights.set(Relay.Value.kOff);
	}

    public void initDefaultCommand() {
        
    }
    
    public static Lights getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Lights();
		}
	}
	
	public void enable() {
		lights.set(Relay.Value.kOn);
	}
	
	public void disable() {
		lights.set(Relay.Value.kOff);
	}
	
	public void set(Relay.Value val) {
		lights.set(val);
	}
	
	public void swap() {
		if(isEnabled()) {
			disable();
		} else {
			enable();
		}
	}
	
	public boolean isEnabled() {
		if(get() == Relay.Value.kOff) {
			return false;
		}
		return true;
	}
	
	public Relay.Value get() {
		return lights.get();
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putData("Lights", lights);
	}
}


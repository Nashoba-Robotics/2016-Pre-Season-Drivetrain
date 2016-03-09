package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.TalonEncoder;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveJoystickCommand;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LoaderRoller extends Subsystem implements SmartDashboardSource {
    
	CANTalon talon;
		
	private static LoaderRoller singleton;
	
	DigitalInput gate;
	
	private LoaderRoller() {
		
		gate = new DigitalInput(RobotMap.LOADER_PHOTO_GATE);
		
		talon = new CANTalon(RobotMap.LOADER_ROLLER_TALON);
		talon.enableBrakeMode(true);

	}
	
	public static LoaderRoller getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new LoaderRoller();
		}
	}
	
	/**
	 * Set the loader PID to the given value
	 * @param val the value to set the loader setpoint to
	 */
	public void setLoaderSpeed(double val) {
		talon.set(val);
	}
	
    public void initDefaultCommand() {
		setDefaultCommand(new LoaderRollerJoystickCommand());
    }

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putData(this);
	}
	
	public boolean hasBall() {
		return !gate.get();
	}

	public boolean isRunning() {
		return Math.abs(talon.get()) > 0.1 ;
	}
}


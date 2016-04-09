package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.interfaces.SmartDashboardSource;
import edu.nr.robotics.EnabledSubsystems;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LoaderRoller extends Subsystem implements SmartDashboardSource {
    
	private CANTalon talon;
		
	private static LoaderRoller singleton;
	
	private DigitalInput loadergate, intakegate, shootergate;

	private LoaderRoller() {
		if(EnabledSubsystems.loaderRollersEnabled) {
			loadergate = new DigitalInput(RobotMap.LOADER_PHOTO_GATE);
			intakegate = new DigitalInput(RobotMap.INTAKE_PHOTO_GATE);
			shootergate = new DigitalInput(RobotMap.SHOOTER_PHOTO_GATE);
			talon = new CANTalon(RobotMap.LOADER_ROLLER_TALON);
			talon.enableBrakeMode(true);
		}
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
		if(talon != null)
			talon.set(val);
	}
	
    @Override
	public void initDefaultCommand() {
		setDefaultCommand(new LoaderRollerKeepAtPhotoCommand());
    }
	
	public boolean hasLoaderBall() {
		if(loadergate != null)
			return !loadergate.get();
		return false;
	}
	
	public boolean hasIntakeBall() {
		if(intakegate != null)
			return !intakegate.get();
		return false;
	}

	public boolean hasShooterBall() {
		if(shootergate != null)
			return !shootergate.get();
		return false;
	}

	@Override
	public void smartDashboardInfo() {
		if(EnabledSubsystems.loaderRollersEnabled) {
			SmartDashboard.putBoolean("Loader Roller Forward", isForward());
			SmartDashboard.putBoolean("Loader Roller Reverse", isReverse());
			SmartDashboard.putData(this);
		}
	}
	
	public boolean isForward() {
		if(talon != null)
			return talon.get() < -0.1 ;
		return false;
	}
	
	public boolean isReverse() {
		if(talon != null)
			return talon.get() > 0.1 ;
		return false;
	}
}


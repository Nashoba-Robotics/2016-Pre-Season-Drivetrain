package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.TalonEncoder;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem implements SmartDashboardSource{
    
	private static final double FULL_SPEED = 0; //TODO: Find the full speed of the shooter

	private static Shooter singleton;

	CANTalon talon;
	TalonEncoder enc;
	PID pid;
	
	private Shooter() {
		talon = new CANTalon(RobotMap.SHOOTER_TALON_A);
		talon.enableBrakeMode(true);

		CANTalon talonTemp = new CANTalon(RobotMap.SHOOTER_TALON_B);
		talonTemp.changeControlMode(TalonControlMode.Follower);
		talonTemp.set(talon.getDeviceID());
		talonTemp.enableBrakeMode(true);
		
		enc = new TalonEncoder(talon);
		enc.setPIDSourceType(PIDSourceType.kRate);
				
		pid = new PID(0.0001, 0, 0, enc, talon); //TODO: Get the value for the Shooter PID

	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterOffCommand());
    }
    
    public static Shooter getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Shooter();
		}
	}
	
	/**
	 * Disable the PID and set the motor to the given speed
	 * @param speed the speed to set the motor to, from -1 to 1
	 */
	public void setMotor(double speed) {
		pid.disable();
		talon.set(speed);
	}
	
	/**
	 * Set the PID setpoint and enables the PID
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		pid.enable();
		pid.setSetpoint(value);	
	}
	
	/**
	 * Get the PID setpoint
	 * @return the PID setpoint
	 */
	public double getSetpoint() {
		return pid.getSetpoint();
	}
	
	/**
	 * Enable the PID
	 */
	public void enable() {
		if(!pid.isEnable()) {
			pid.enable();
		}
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		if(pid.isEnable()) {
			pid.disable();
		}
	}
	
	/**
	 * Gets whether the PIDs are enabled or not
	 * technically only checks one of the PIDs, but they should be the same
	 * @return whether the PIDs are enabled
	 */
	public boolean isPIDEnable() {
		return pid.isEnable();
	}
	
	/**
	 * Gets the speed of the shooter
	 * @return the speed of the shooter
	 */
	public double getSpeed() {
		return enc.getRate();
	}
	
	public boolean getRunning() {
		return getSpeed() > 0.1;
	}
	
	public double getSpeedPercent() {
		return enc.getRate() / FULL_SPEED;
	}
	
	/**
	 * Tells if the shooter is up to speed
	 * @return
	 */
	public boolean getSped() {
		return getSpeed() > FULL_SPEED;
	}

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Shooter Speed", getSpeed());
		SmartDashboard.putBoolean("Shooter Running", getSetpoint() != 0);
		SmartDashboard.putNumber("Shooter Current", talon.getOutputCurrent());
	}
}
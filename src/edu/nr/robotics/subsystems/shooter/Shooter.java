package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.CounterPIDSource;
import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem implements SmartDashboardSource{
    
	private static Shooter singleton;

	CANTalon talonA, talonB;
	PID pid;
		
	MotorSetter talonOutput;
	
	DigitalInput gate;

	
	//In rotations per second
	CounterPIDSource shooterRate;
	
	private Shooter() {
		gate = new DigitalInput(RobotMap.SHOOTER_PHOTO_GATE);
		
		talonA = new CANTalon(RobotMap.SHOOTER_TALON_A);
		talonA.enableBrakeMode(false);
		
		talonB = new CANTalon(RobotMap.SHOOTER_TALON_B);
		talonB.enableBrakeMode(false);
		talonB.setInverted(true);

		talonOutput = new MotorSetter(talonA, talonB);
		
		shooterRate = new CounterPIDSource(RobotMap.SHOOTER_RATE_PORT);
		shooterRate.setPIDSourceType(PIDSourceType.kRate);
		shooterRate.setSamplesToAverage(24);
		shooterRate.scale(2 * RobotMap.SHOOTER_MAX_SPEED);
		
    	talonOutput.setTalonRampRate(RobotMap.SHOOTER_RAMP_RATE);

						
		pid = new PID(0.75, 0.0001, 0, 1, shooterRate, talonOutput);
	}
	
    public void initDefaultCommand() {
    	//setDefaultCommand(new ShooterJoystickCommand());
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
		if(pid.isEnable())
			pid.disable();
		talonOutput.write(speed);
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
	public double getScaledSpeed() {
		return shooterRate.pidGet();
	}
	
	public double getSpeed() {
		return getScaledSpeed() * RobotMap.SHOOTER_MAX_SPEED;
	}
	
	public boolean getRunning() {
		return getScaledSpeed() > 0.1;
	}
	
	public double getSpeedPercent() {
		return shooterRate.getRate() / RobotMap.SHOOTER_MAX_SPEED;
	}
	
	/**
	 * Tells if the shooter is up to speed
	 * @return
	 */
	public boolean getSped() {
		return getSpeedPercent() > getSetpoint();
	}

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Shooter Speed", getSpeed());
		SmartDashboard.putBoolean("Shooter Running", getRunning());
		SmartDashboard.putNumber("Shooter Current", talonOutput.getOutputCurrent());
		SmartDashboard.putData(this);
		SmartDashboard.putData("Shooter PID", pid);
		SmartDashboard.putNumber("Shooter Output", pid.get());
	}

	public boolean hasBall() {
		return !gate.get();
	}
}
package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.CounterPIDSource;
import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem implements SmartDashboardSource, Periodic{
    
	private static Shooter singleton;

	CANTalon talonA, talonB;
		
	MotorSetter talonOutput;
	
	DigitalInput gate;
	
	double shooterGoal = 0;
	public double talonOutputSpeed = 0;

	
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
	 * Set the PID setpoint and enables the PID
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		shooterGoal = value;
	}
	
	/**
	 * Get the PID setpoint
	 * @return the PID setpoint
	 */
	public double getSetpoint() {
		return shooterGoal;
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
		SmartDashboard.putNumber("Shooter Output", talonOutputSpeed);
	}

	public boolean hasBall() {
		return !gate.get();
	}

	@Override
	public void periodic() {
		if(shooterRate.get() < (shooterGoal - 0.05)) {
			talonOutputSpeed += 0.05;
		} else if(shooterRate.get() > (shooterGoal + 0.05)) {
			talonOutputSpeed -= 0.05;
		}
	}
}
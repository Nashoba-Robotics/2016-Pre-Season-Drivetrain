package edu.nr.robotics.subsystems.rollers;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
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
public class Rollers extends Subsystem implements SmartDashboardSource {
    
	CANTalon loaderTalon;
	CANTalon intakeTalon;
	
	Encoder loaderEncoder;
	Encoder intakeEncoder;
	
	PID loaderPID;
	PID intakePID;
	
	private static Rollers singleton;
	
	private Rollers() {
		loaderTalon = new CANTalon(RobotMap.ROLLER_LOADER_TALON);

		intakeTalon = new CANTalon(RobotMap.ROLLER_INTAKE_TALON);

		loaderEncoder = new Encoder(RobotMap.ROLLER_LOADER_ENCODER_A, RobotMap.ROLLER_LOADER_ENCODER_B);
		intakeEncoder = new Encoder(RobotMap.ROLLER_INTAKE_ENCODER_A, RobotMap.ROLLER_INTAKE_ENCODER_B);

		loaderEncoder.setPIDSourceType(PIDSourceType.kRate);
		intakeEncoder.setPIDSourceType(PIDSourceType.kRate);

		loaderPID = new PID(0, 0, 0, loaderEncoder, loaderTalon);
		intakePID = new PID(0, 0, 0, intakeEncoder, intakeTalon);
		//TODO: Get the value for the Rollers PID
		loaderPID.enable();
		intakePID.enable();
	}
	
	public static Rollers getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Rollers();
		}
	}
	
	/**
	 * Set both the intake and the loader PIDs to the given values
	 * @param val the value to set the setpoints to
	 */
	public void setSetpoints(double val) {
		loaderPID.setSetpoint(val);
		intakePID.setSetpoint(val);
	}
	
	/**
	 * Set the loader PID to the given value
	 * @param val the value to set the loader setpoint to
	 */
	public void setLoaderSetpoint(double val) {
		loaderPID.setSetpoint(val);
	}
	
	/**
	 * Set the intake PID to the given value
	 * @param val the value to set the intake setpoint to
	 */
	public void setIntakeSetpoint(double val) {
		intakePID.setSetpoint(val);
	}
	
	public boolean getIntakeRunning() {
		return intakeEncoder.get() > 0.1;
	}
	
	public boolean getLoaderRunning() {
		return loaderEncoder.get() > 0.1;
	}
	
    public void initDefaultCommand() {
    }

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("Loader Encoder Speed", loaderEncoder.getRate());
		SmartDashboard.putNumber("Intake Encoder Speed", intakeEncoder.getRate());
	}

	public boolean getBallInIntake() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getBallInLoader() {
		// TODO Auto-generated method stub
		return false;
	}
}


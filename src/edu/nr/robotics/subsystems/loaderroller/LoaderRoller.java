package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.AnalogIRSensor;
import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LoaderRoller extends Subsystem implements SmartDashboardSource {
    
	CANTalon loaderTalon;
	
	Encoder loaderEncoder;
	
	AnalogIRSensor irSensor;
	
	PID loaderPID;
	
	private static LoaderRoller singleton;
	
	private LoaderRoller() {
		irSensor = new AnalogIRSensor(RobotMap.ROLLER_LOADER_IR_SENSOR);
		
		loaderTalon = new CANTalon(RobotMap.ROLLER_LOADER_TALON);


		loaderEncoder = new Encoder(RobotMap.ROLLER_LOADER_ENCODER_A, RobotMap.ROLLER_LOADER_ENCODER_B);

		loaderEncoder.setPIDSourceType(PIDSourceType.kRate);

		loaderPID = new PID(0, 0, 0, loaderEncoder, loaderTalon);
		//TODO: Get the value for the Rollers PID
		loaderPID.enable();
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
	 * Set both the intake and the loader PIDs to the given values
	 * @param val the value to set the setpoints to
	 */
	public void setSetpoints(double val) {
		loaderPID.setSetpoint(val);
	}
	
	/**
	 * Set the loader PID to the given value
	 * @param val the value to set the loader setpoint to
	 */
	public void setLoaderSetpoint(double val) {
		loaderPID.setSetpoint(val);
	}
	
	public boolean getLoaderRunning() {
		return loaderEncoder.get() > 0.1;
	}
	
    public void initDefaultCommand() {
    }

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("Loader Encoder Speed", loaderEncoder.getRate());
	}
	
	public boolean getBallInLoader() {
		// TODO Auto-generated method stub
		return false;
	}
}


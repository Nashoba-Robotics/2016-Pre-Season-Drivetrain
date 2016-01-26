package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.AnalogIRSensor;
import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeArm extends Subsystem implements SmartDashboardSource{
    
	private static IntakeArm singleton;

	CANTalon armTalon;
	CANTalon rollerTalon;
	
	Encoder rollerEncoder;
	PID rollerPID;
	
	AnalogIRSensor irSensor;

	AnalogPotentiometer armPot;
	PID armPID;
	
	private IntakeArm() {
		irSensor = new AnalogIRSensor(RobotMap.ROLLER_LOADER_IR_SENSOR);
		
		rollerTalon = new CANTalon(RobotMap.ROLLER_INTAKE_TALON);
		rollerEncoder = new Encoder(RobotMap.ROLLER_INTAKE_ENCODER_A, RobotMap.ROLLER_INTAKE_ENCODER_B);
		rollerEncoder.setPIDSourceType(PIDSourceType.kRate);
		rollerPID = new PID(0, 0, 0, rollerEncoder, rollerTalon); //TODO: Get the PID value
		rollerPID.enable();

		armTalon = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		armPot = new AnalogPotentiometer(RobotMap.INTAKE_ARM_POT);
		armPot.setPIDSourceType(PIDSourceType.kDisplacement);
		armPID = new PID(0.0001, 0, 0, armPot, armTalon); //TODO: Get the value for the Intake Arm PID
	}
	
    public void initDefaultCommand() {
    }
    
    public static IntakeArm getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new IntakeArm();
		}
	}
	
	/**
	 * Disable the PID and set the motor to the given speed
	 * @param speed the speed to set the motor to, from -1 to 1
	 */
	public void setMotor(double speed) {
		armPID.disable();
		armTalon.set(speed);
	}
	
	/**
	 * Set the arm PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setArmSetpoint(double value) {
		armPID.setSetpoint(value);	
	}
	
	/**
	 * Set the roller PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setRollerSetpoint(double value) {
		rollerPID.setSetpoint(value);	
	}
	
	/**
	 * Get the PID setpoint
	 * @return the PID setpoint
	 */
	public double getSetpoint() {
		return armPID.getSetpoint();
	}
	
	public boolean getRollerRunning() {
		return rollerEncoder.get() > 0.1;
	}
	
	/**
	 * Enable the PID
	 */
	public void enable() {
		armPID.enable();
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		armPID.disable();
	}
	
	/**
	 * Gets whether the PID is enabled or not
	 * @return whether the PID is enabled
	 */
	public boolean isEnable() {
		return armPID.isEnable();
	}
	/**
	 * Gets the value of the potentiometer
	 * @return the value of the potentiometer
	 */
	public double get() {
		return armPot.get();
	}
	
	public boolean getBallInIntake() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Gets whether the motor is still moving
	 * @return whether the motor is still moving
	 */
	public boolean getMoving() {
		return Math.abs(armPID.getError()) > 0.05;
		//0.05 is a number I just made up
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("Intake Arm Potentiometer", get());
		SmartDashboard.putBoolean("Intake Arm Moving", Math.abs(armPID.getError()) > 0.05);
		SmartDashboard.putNumber("Intake Roller Speed", rollerEncoder.getRate());
	}
}


package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.TalonEncoder;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerJoystickCommand;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Hood extends Subsystem implements SmartDashboardSource, Periodic {
    
	CANTalon talon;	
	TalonEncoder enc;
	PID pid;
	
	double maxSpeed = 1.0;
	
	private static Hood singleton;
	
	public enum Position {
		BOTTOM (RobotMap.HOOD_BOTTOM_POSITION), TOP(RobotMap.HOOD_TOP_POSITION);
		
		public final double pos;
		Position(double position) {
			this.pos = position;
		}
	}
	
	private Hood() {
		talon = new CANTalon(RobotMap.HOOD_TALON);
		talon.enableBrakeMode(true);
		enc = new TalonEncoder(talon);
		enc.setPIDSourceType(PIDSourceType.kDisplacement);
		enc.setDistancePerRev(RobotMap.HOOD_TICK_TO_ANGLE_MULTIPLIER);
		pid = new PID(0.25, 0.00, 0.001, enc, talon);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new HoodJoystickCommand());
	}
	
	public void resetEncoder() {
		enc.reset();
	}
	
	public static Hood getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Hood();
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
	 * Set the PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		if(!pid.isEnable())
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
		pid.enable();
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		pid.disable();
	}
	
	/**
	 * Gets whether the PID is enabled or not
	 * @return whether the PID is enabled
	 */
	public boolean isEnable() {
		return pid.isEnable();
	}
	
	/**
	 * Gets the value of the encoder
	 * @return the value of the encoder
	 */
	public double get() {
		return enc.get() ;
	}
	
	/**
	 * Gets whether the motor is still moving
	 * @return whether the motor is still moving
	 */
	public boolean getMoving() {
		return Math.abs(pid.getError()) > 0.05;
		//0.05 is a number I just made up
	}
	
	
	//Note: the two angle/distance functions aren't inverses of each other
	//The distanceToAngle is more accurate, but the inverse of it is hard to calculate
	public static double distanceToAngle(double distance) {
		return  0.0095*Math.pow(distance, 3) - 0.4725*Math.pow(distance, 2) + 8.2134*Math.pow(distance, 1) + 9.1025;
	}

	public static double angleToDistance(double angle) {
		return 0.334902 * Math.exp(0.0657678 * angle);
	}
	
	public void setMaxSpeedPID(double speed) {
		maxSpeed = speed;
		pid.setOutputRange(-speed, speed);
	}
	
	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Hood Encoder", get());
		SmartDashboard.putBoolean("Hood Moving", getMoving());
		SmartDashboard.putData(this);
		
		SmartDashboard.putBoolean("Hood top limit switch", isTopLimitSwitchClosed());
		SmartDashboard.putBoolean("Hood bot limit switch", isBotLimitSwitchClosed());

	}

	public boolean isAtPosition(Position pos) {
		return enc.get() + RobotMap.HOOD_THRESHOLD > pos.pos &&  enc.get() - RobotMap.HOOD_THRESHOLD < pos.pos;
	}

	public boolean isAtBottom() {
		return isAtPosition(Position.BOTTOM);
	}

	public boolean isAtTop() {
		return isAtPosition(Position.TOP);
	}

	@Override
	public void periodic() {
		if(isBotLimitSwitchClosed()) {
			enc.reset();
		}
	}

	public boolean isTopLimitSwitchClosed() {
		return talon.isFwdLimitSwitchClosed();
	}
	
	public boolean isBotLimitSwitchClosed() {
		return talon.isRevLimitSwitchClosed();
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	
}


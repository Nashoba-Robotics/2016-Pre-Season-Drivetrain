package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRMath;
import edu.nr.lib.TalonEncoder;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.lib.interfaces.SmartDashboardSource;
import edu.nr.robotics.LiveWindowClasses;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem implements SmartDashboardSource, Periodic {
    
	CANTalon talon;
	public TalonEncoder enc;
	
	private static Elevator singleton;
	
	public enum Position {
		BOTTOM (RobotMap.ELEVATOR_BOTTOM_POSITION), TOP(RobotMap.ELEVATOR_TOP_POSITION);
		
		public final double pos;
		Position(double position) {
			this.pos = position;
		}
	}
	
	private Elevator() {
		talon = new CANTalon(RobotMap.ELEVATOR_TALON);
		talon.enableBrakeMode(true);
		enc = new TalonEncoder(talon);
		
		LiveWindow.addSensor("Elevator", "Speed", LiveWindowClasses.elevatorSpeed);
		LiveWindow.addSensor("Elevator", "Switch", LiveWindowClasses.elevatorSwitch);
	}
	
	public static Elevator getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Elevator();
		}
	}
	
	/**
	 * Set the motor to a value from -1 to 1
	 * @param val the value from -1 to 1
	 */
	public void setMotorValue(double val) {
		val = NRMath.limit(val, 1);
		talon.set(val);
	}
	
	public double getMotorValue() {
		return talon.get();
	}
	
	public void resetEncoder() {
		enc.reset();
	}
	
	public double getEncoder() {
		return enc.get();
	}

	
    @Override
	public void initDefaultCommand() {
		setDefaultCommand(new ElevatorJoystickCommand());
    }

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Elevator speed", enc.getRate());
		SmartDashboard.putNumber("Elevator position", enc.get());
		SmartDashboard.putBoolean("Elevator moving", getMotorValue() != 0);
		LiveWindowClasses.elevatorSpeed.set(enc.getRate());
		LiveWindowClasses.elevatorSwitch.set(isLimitSwitchClosed());
	}

	public boolean isMoving() {
		return enc.getRate() > 10;
	}
	
	public boolean isAtPosition(Position pos) {
		return enc.get() + RobotMap.ELEVATOR_THRESHOLD > pos.pos &&  enc.get() - RobotMap.ELEVATOR_THRESHOLD < pos.pos;
	}

	public boolean isAtBottom() {
		return isAtPosition(Position.BOTTOM);
	}

	public boolean isAtTop() {
		return isAtPosition(Position.TOP);
	}

	public boolean isLimitSwitchClosed() {
		return false;//talon.isFwdLimitSwitchClosed();
		//TODO: Is it the fwd limit switch or the back limit switch?
	}

	@Override
	public void periodic() {
		if(isLimitSwitchClosed())
			resetEncoder();
	}
	
}


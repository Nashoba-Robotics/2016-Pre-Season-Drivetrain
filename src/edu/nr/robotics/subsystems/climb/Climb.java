package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRMath;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.TalonEncoder;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climb extends Subsystem implements SmartDashboardSource {
    
	CANTalon talon;
	public TalonEncoder enc;
	
	private static Climb singleton;
	
	public enum Position {
		BOTTOM (RobotMap.ELEVATOR_BOTTOM_POSITION), TOP(RobotMap.ELEVATOR_TOP_POSITION);
		
		public final double pos;
		Position(double position) {
			this.pos = position;
		}
	}
	
	private Climb() {
		talon = new CANTalon(RobotMap.ELEVATOR_TALON);
		talon.enableBrakeMode(true);
		enc = new TalonEncoder(talon);
	}
	
	public static Climb getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Climb();
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

	
    public void initDefaultCommand() {
    }

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Elevator Speed", enc.getRate());
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
}


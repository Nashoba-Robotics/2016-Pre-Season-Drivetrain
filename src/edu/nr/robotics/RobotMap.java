package edu.nr.robotics;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static final long LIGHTS_BLINK_PERIOD = 200; //in milliseconds

	public static final int TALON_RIGHT_A = 4;
	public static final int TALON_RIGHT_B = 16;
	public static final int TALON_LEFT_A = 14;
	public static final int TALON_LEFT_B = 15;

	public static final int ENCODER_LEFT_A = 9;
	public static final int ENCODER_LEFT_B = 8;
	public static final int ENCODER_RIGHT_A = 7;
	public static final int ENCODER_RIGHT_B = 6;
	public static final double MAX_SPEED = 3.4; // Meters per second //Actual
												// value is 3.4 m/s
	// TODO: Get actual max speed instead of using a placeholder value
	
	public static final int LIGHTS_SPIKE = 0;
	
	//TODO: Get values for ports
	public static final int INTAKE_ARM_TALON = -1;
	public static final int INTAKE_ARM_POT = -1;
	public static final int SHOOTER_ENCODER_A = -1;
	public static final int SHOOTER_ENCODER_B = -1;
	public static final int HOOD_TALON = -1;
	public static final int HOOD_POT = -1;
	public static final int ELEVATOR_TALON = -1;
	
	public static final int ROLLER_LOADER_TALON     = -1;
	public static final int ROLLER_LOADER_ENCODER_A = -1;
	public static final int ROLLER_LOADER_ENCODER_B = -1;
	public static final int ROLLER_LOADER_IR_SENSOR = -1;

	public static final int ROLLER_INTAKE_TALON     = -1;
	public static final int ROLLER_INTAKE_ENCODER_A = -1;
	public static final int ROLLER_INTAKE_ENCODER_B = -1;
	public static final int ROLLER_INTAKE_IR_SENSOR = -1;
	public static final int SHOOTER_TALON_A = -1;
	public static final int SHOOTER_TALON_B = -1;
	
	//TODO: Find all the values
	public static final double SHOOTER_FAST_SPEED = -1; //out of 1.0
	public static final double SHOOTER_SLOW_SPEED = -1; //out of 1.0
	public static final double SHOOTER_MAX_SPEED = -1; //In ticks per minute
	public static final double CLOSE_SHOT_POSITION = -1;
	public static final double LONG_SHOT_POSITION = -1;
	public static final double SHOOTER_RAMP_RATE = 1;
	public static final double INTAKE_ARM_UP_HEIGHT = -1;
	public static final double INTAKE_ARM_BOTTOM_HEIGHT = -1;
	public static final double INTAKE_ARM_INTAKE_HEIGHT = -1;
	public static final double INTAKE_ARM_BUMPER_HEIGHT = -1;
	public static final double ELEVATOR_EXTEND_DISTANCE = -1;
	public static final double HOOD_BOTTOM_POSITION = -1;
	public static final double HOOD_TOP_POSITION = -1;
	public static final double HOOD_THRESHOLD = -1;
	public static final int INTAKE_TOP_POS = -1;
	public static final int INTAKE_THRESHOLD = -1;
	public static final int INTAKE_INTAKE_POS = -1;
	public static final int INTAKE_HOME_POS = -1;
	public static final int INTAKE_BOTTOM_POS = -1;
	public static final int ELEVATOR_BOTTOM_POSITION = -1;
	public static final int ELEVATOR_TOP_POSITION = -1;
	public static final double ELEVATOR_THRESHOLD = -1;
	public static final double TURN_THRESHOLD = -1;
	public static final double SHOOTER_THRESHOLD = -1;


}

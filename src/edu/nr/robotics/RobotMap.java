package edu.nr.robotics;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

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
	public static final double MAX_ACCELERATION = 3.0; // Meters per second per
														// second
	// TODO: Get actual max values instead of using a placeholder value
	
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
	
	//TODO: Find the shooter fast and slow speeds
	public static final double SHOOTER_FAST_SPEED = 0.7;
	public static final double SHOOTER_SLOW_SPEED = 0.4;
	
	public static final long LIGHTS_BLINK_PERIOD = 200; //in milliseconds
	
	//TODO: find the close shot hood position
	public static final double CLOSE_SHOT_POSITION = 5;
	
	//TODO: find the shooter ramp rate
	public static final double SHOOTER_RAMP_RATE = 1;

}

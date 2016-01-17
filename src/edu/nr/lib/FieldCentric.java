package edu.nr.lib;

import edu.nr.lib.navx.NavX;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldCentric implements SmartDashboardSource {

	// NOTE: X is forward, Y is side-to-side

	private static FieldCentric singleton;
	private final double initialTheta;
	private double initialGyro = 0;
	private double x = 0, y = 0, dis = 0, lastEncoderDistance = 0;
	private long lastUpdateTime;

	public static FieldCentric getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new FieldCentric(Math.PI / 2);
		}
	}

	public FieldCentric(double initialTheta) {
		lastUpdateTime = System.currentTimeMillis();
		this.initialTheta = initialTheta;
	}

	/**
	 * Updates the model based on the current Drive values
	 */
	public void update() {
		if (System.currentTimeMillis() - lastUpdateTime > 300) {
			System.err.println("WARNING: FieldCentric not being called often enough: ("
					+ (System.currentTimeMillis() - lastUpdateTime) / 1000f + "s)");
		}

		double angle = getAngleRadians();

		double ave = Drive.getInstance().getEncoderAverageDistance();
		double delta_x_r = ave - lastEncoderDistance;
		double deltax = delta_x_r * Math.sin(angle);
		double deltay = delta_x_r * Math.cos(angle);
		x += deltax;
		y += deltay;
		dis += delta_x_r;

		lastEncoderDistance = ave;

		lastUpdateTime = System.currentTimeMillis();
	}

	/**
	 * Gets the distance the robot has moved since FieldCentric was reset
	 * 
	 * @return the distance in meters
	 */
	public double getDistance() {
		return dis;
	}

	/**
	 * Gets the x distance the robot has moved since FieldCentric was reset
	 * 
	 * @return the x distance in meters
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y distance the robot has moved since FieldCentric was reset
	 * 
	 * @return the y distance in meters
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the Position change the robot has moved since FieldCentric was reset
	 * 
	 * @return the Position, where the values are in meters
	 */
	public Position getPosition() {
		return new Position(x, y);
	}

	/**
	 * Gets the angle used for current coordinate calculations
	 * 
	 * @return the angle in radians
	 */
	public double getAngleRadians() {
		// Gyro is reversed (clockwise causes an increase in the angle)
		return ((NavX.getInstance().getYawRad()) - initialGyro) * -1 + initialTheta;
	}

	/**
	 * Resets the model
	 */
	public void reset() {
		x = 0;
		y = 0;
		lastEncoderDistance = Drive.getInstance().getEncoderAverageDistance();
		initialGyro = NavX.getInstance().getYawRad();
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("NavX Yaw", NavX.getInstance().getYawDeg());
		SmartDashboard.putNumber("NavX Pitch", NavX.getInstance().getPitchDeg());
		SmartDashboard.putNumber("NavX Roll", NavX.getInstance().getRollDeg());

		SmartDashboard.putNumber("Gyro", NavX.getInstance().getYawRad());
	}
}

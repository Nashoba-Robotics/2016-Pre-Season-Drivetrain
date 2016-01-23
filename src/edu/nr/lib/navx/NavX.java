package edu.nr.lib.navx;

import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRMath;
import edu.nr.lib.SmartDashboardSource;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX implements SmartDashboardSource {
	private SerialPort serial_port;
	private IMU imu;

	private NavX() {
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);

			byte update_rate_hz = 100;
			// imu = new IMU(serial_port,update_rate_hz);
			imu = new IMUAdvanced(serial_port, update_rate_hz);
		} catch (Exception e) {
			System.out.println("ERROR: An error occured while initializing the MXP Board");
		}
	}

	int fullRotationCount = 0;
	double lastYaw;

	/**
	 * Gets the current yaw of the robot in the given units
	 * @param unit the {@link AngleUnit} to return in
	 * @return the yaw
	 */
	public double getYaw(AngleUnit unit) {
		if (imu != null && imu.isConnected()) {
			double currentYaw = imu.getYaw();
			if ((lastYaw < -90 || lastYaw > 90) && (currentYaw > 90 || currentYaw < -90)) {
				if (lastYaw < 0 && currentYaw > 0) {
					fullRotationCount--;
				} else if (lastYaw > 0 && currentYaw < 0) {
					fullRotationCount++;
				}
			}

			lastYaw = currentYaw;
			double valueDeg = currentYaw + 360 * fullRotationCount;
			if(unit == AngleUnit.DEGREE) {
				return valueDeg;
			}
			if(unit == AngleUnit.RADIAN) {
				return Math.toRadians(valueDeg);
			}
		}
		return 0;
	}

	/**
	 * Gets the current roll of the robot in the given units
	 * @param unit the {@link AngleUnit} to return in
	 * @return the roll
	 */
	public double getRoll(AngleUnit unit) {
		if (imu != null && imu.isConnected()) {
			if(unit == AngleUnit.DEGREE) {
				return imu.getRoll();
			}
			if(unit == AngleUnit.RADIAN) {
				return Math.toRadians(imu.getRoll());
			}
		}
		return 0;
	}

	/**
	 * Gets the current pitch of the robot in the given units
	 * @param unit the {@link AngleUnit} to return in
	 * @return the pitch
	 */
	public double getPitch(AngleUnit unit) {
		if (imu != null && imu.isConnected()) {
			if(unit == AngleUnit.DEGREE) {
				return imu.getPitch();
			}
			if(unit == AngleUnit.RADIAN) {
				return Math.toRadians(imu.getPitch());
			}
		}
		return 0;
	}

	/**
	 * Resets the yaw counter (that is, it zeros the yaw at the current position)
	 */
	public void resetYaw() {
		if (imu != null && imu.isConnected()) {
			imu.zeroYaw();
		}
	}

	// Singleton code
	private static NavX singleton;

	public static NavX getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new NavX();
		}
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("NavX Yaw", getYaw(AngleUnit.DEGREE));
		SmartDashboard.putNumber("NavX Roll", getRoll(AngleUnit.DEGREE));
		SmartDashboard.putNumber("NavX Pitch", getPitch(AngleUnit.DEGREE));
	}
}

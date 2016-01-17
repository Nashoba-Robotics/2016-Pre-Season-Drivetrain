package edu.nr.lib.navx;

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
	 * Gets the current yaw of the robot in degrees
	 * @return the yaw in degrees
	 */
	public double getYawDeg() {
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
			return currentYaw + 360 * fullRotationCount;
		}
		return 0;
	}
	
	/**
	 * Gets the current yaw of the robot in radians
	 * @return the yaw in radians
	 */
	public double getYawRad() {
		return NRMath.degToRad(getYawDeg());
	}

	/**
	 * Gets the current roll of the robot in degrees
	 * @return the roll in degrees
	 */
	public double getRollDeg() {
		if (imu != null && imu.isConnected()) {
			return imu.getRoll();
		}
		return 0;
	}
	
	/**
	 * Gets the current roll of the robot in radians
	 * @return the roll in radians
	 */
	public double getRollRad() {
		return NRMath.degToRad(getRollDeg());
	}

	/**
	 * Gets the current pitch of the robot in degrees
	 * @return the pitch in degrees
	 */
	public double getPitchDeg() {
		if (imu != null && imu.isConnected()) {
			return imu.getPitch();
		}
		return 0;
	}
	
	/**
	 * Gets the current pitch of the robot in radians
	 * @return the pitch in radians
	 */
	public double getPitchRad() {
		return NRMath.degToRad(getPitchDeg());
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
		SmartDashboard.putNumber("NavX Yaw", getYawDeg());
		SmartDashboard.putNumber("NavX Roll", getRollDeg());
		SmartDashboard.putNumber("NavX Pitch", getPitchDeg());
	}
}

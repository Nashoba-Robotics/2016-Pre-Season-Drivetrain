package edu.nr.lib;

public class NRMath {

	/**
	 * Squares the input value and maintains the sign
	 * @param x
	 * @return the squared value with the sign maintained
	 */
	public static double squareWithSign(double x) {
		return x * x * Math.signum(x);
	}

	/**
	 * Limits x from -y to y
	 * @param x
	 * @param y
	 * @return the value, limited from -y to y
	 */
	public static double limit(double x, double y) {
		if (x > y) {
			return y;
		}
		if (x < -y) {
			return -y;
		}
		return x;
	}

	/**
	 * Converts a value from degrees to radians
	 * @param degrees the value in degrees
	 * @return the value in radians
	 */
	public static double degToRad(double d) {
		return 2 * Math.PI * d / 360.0;
	}

	/**
	 * Converts a value from radians to degrees
	 * @param radians the value in radians
	 * @return the value in degrees
	 */
	public static double radToDeg(double r) {
		return r * 360.0 / (2 * Math.PI);
	}

}

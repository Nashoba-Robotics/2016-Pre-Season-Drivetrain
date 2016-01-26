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
}

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
	 * Limits the value given from -1 to 1
	 * @param x
	 * @return the value, limited from -1 to 1
	 */
	public static double limit(double x) {
		if (x > 1.0) {
			return 1.0;
		}
		if (x < -1.0) {
			return -1.0;
		}
		return x;
	}
}

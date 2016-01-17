package edu.nr.lib;

public class NRMath {

	public static double squareWithSign(double x) {
		return x * x * Math.signum(x);
	}

	public static double limit(double x) {
		if (x > 1.0) {
			return 1.0;
		}
		if (x < -1.0) {
			return -1.0;
		}
		return x;
	}

	public static double degToRad(double d) {
		return 2 * Math.PI * d / 360.0;
	}

	public static double radToDeg(double d) {
		return d * 360.0 / (2 * Math.PI);
	}

}

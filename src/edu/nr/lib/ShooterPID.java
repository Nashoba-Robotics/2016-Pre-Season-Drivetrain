package edu.nr.lib;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * Class implements a PID Control Loop.
 *
 * Creates a separate thread which reads the given PIDSource and takes care of
 * the integral calculations, as well as writing the given PIDOutput
 */
public class ShooterPID implements LiveWindowSendable {
	
	boolean smartDashboardPrintingP = false;
	boolean smartDashboardPrintingF = false;

	public static final double kDefaultPeriod = .02;
	private double m_P; // factor for "proportional" control
	private double m_F; // factor for feedforward term
	private boolean m_enabled = false; // is the pid controller enabled
	private double m_setpoint = 0.0;
	private double m_error = 0.0;
	private double m_result = 0.0;
	PIDSource m_pidInput;
	PIDOutput m_pidOutput;
	java.util.Timer m_controlLoop;

	private String subsystemName = "";

	/**
	 * Tolerance is the type of tolerance used to specify if the PID controller
	 * is on target. The various implementations of this class such as
	 * PercentageTolerance and AbsoluteTolerance specify types of tolerance
	 * specifications to use.
	 */
	public interface Tolerance {
		public boolean onTarget();
	}

	public class PercentageTolerance implements Tolerance {
		double percentage;

		PercentageTolerance(double value) {
			percentage = value;
		}

		@Override
		public boolean onTarget() {
			return Math.abs(getError()) < percentage / 100;
		}
	}

	public class AbsoluteTolerance implements Tolerance {
		double value;

		AbsoluteTolerance(double value) {
			this.value = value;
		}

		@Override
		public boolean onTarget() {
			return Math.abs(getError()) < value;
		}
	}

	public class NullTolerance implements Tolerance {

		@Override
		public boolean onTarget() {
			throw new RuntimeException("No tolerance value set when using PID.onTarget()");
		}
	}

	private class PIDTask extends TimerTask {

		private ShooterPID m_controller;

		public PIDTask(ShooterPID controller) {
			if (controller == null) {
				throw new NullPointerException("Given PID was null");
			}
			m_controller = controller;
		}

		@Override
		public void run() {
			m_controller.calculate();
		}
	}

	/**
	 * Allocate a PID object with the given constants for P, I, D, and F
	 * 
	 * @param Kp
	 *            the proportional coefficient
	 * @param Ki
	 *            the integral coefficient
	 * @param Kd
	 *            the derivative coefficient
	 * @param Kf
	 *            the feed forward term
	 * @param source
	 *            The PIDSource object that is used to get values
	 * @param output
	 *            The PIDOutput object that is set to the output percentage
	 * @param period
	 *            the loop time for doing calculations. This particularly
	 *            effects calculations of the integral and differential terms.
	 *            The default is 50ms.
	 */
	public ShooterPID(double Kp, double Kf, PIDSource source, PIDOutput output, double period) {

		if (source == null) {
			throw new NullPointerException("Null PIDSource was given");
		}
		if (output == null) {
			throw new NullPointerException("Null PIDOutput was given");
		}

		m_P = Kp;
		m_F = Kf;

		m_pidInput = source;
		m_pidOutput = output;
		
		m_controlLoop = new java.util.Timer();
		m_controlLoop.schedule(new PIDTask(this), 0L, (long) (period * 1000));
	}

	/**
	 * Allocate a PID object with the given constants for P, I, D and period
	 * 
	 * @param Kp
	 *            the proportional coefficient
	 * @param source
	 *            the PIDSource object that is used to get values
	 * @param output
	 *            the PIDOutput object that is set to the output percentage
	 * @param period
	 *            the loop time for doing calculations. This particularly
	 *            effects calculations of the integral and differential terms.
	 *            The default is 50ms.
	 */
	public ShooterPID(double Kp, PIDSource source, PIDOutput output, double period) {
		this(Kp, 0.0, source, output, period);
	}

	/**
	 * Allocate a PID object with the given constants for P, I, D, using a 50ms
	 * period.
	 * 
	 * @param Kp
	 *            the proportional coefficient
	 * @param source
	 *            The PIDSource object that is used to get values
	 * @param output
	 *            The PIDOutput object that is set to the output percentage
	 */
	public ShooterPID(double Kp, PIDSource source, PIDOutput output) {
		this(Kp, source, output, kDefaultPeriod);
	}

	/**
	 * Allocate a PID object with the given constants for P, I, D, using a 50ms
	 * period.
	 * 
	 * @param Kp
	 *            the proportional coefficient
	 * @param Kf
	 *            the feed forward term
	 * @param source
	 *            The PIDSource object that is used to get values
	 * @param output
	 *            The PIDOutput object that is set to the output percentage
	 */
	public ShooterPID(double Kp, double Kf, PIDSource source, PIDOutput output) {
		this(Kp, Kf, source, output, kDefaultPeriod);
	}

	/**
	 * Free the PID object
	 */
	public void free() {
		m_controlLoop.cancel();
		synchronized (this) {
			m_pidOutput = null;
			m_pidInput = null;
			m_controlLoop = null;
		}
		if (table != null) {
			table.removeTableListener(listener);
		}
	}
	
	/**
	 * Read the input, calculate the output accordingly, and write to the
	 * output. This should only be called by the PIDTask and is created during
	 * initialization.
	 */
	private void calculate() {
		boolean enabled;
		PIDSource pidInput;

		synchronized (this) {
			if (m_pidInput == null) {
				return;
			}
			if (m_pidOutput == null) {
				return;
			}
			enabled = m_enabled; // take snapshot of these values...
			pidInput = m_pidInput;
		}

		if (enabled) {
			double input;
			double result;
			PIDOutput pidOutput = null;
			synchronized (this) {
				input = pidInput.pidGet();
			}
			synchronized (this) {
				m_error = m_setpoint - input;

				if(smartDashboardPrintingP) {
					SmartDashboard.putNumber(subsystemName + " PID P", m_P * m_error);
				} if(smartDashboardPrintingF) {
					SmartDashboard.putNumber(subsystemName + " PID F", m_setpoint * m_F);
				}
				
				m_result = m_P * m_error + m_setpoint * m_F;
				
				pidOutput = m_pidOutput;
				result = m_result;
			}

			pidOutput.pidWrite(result);
		}
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional coefficient.
	 * 
	 * @param p
	 *            Proportional coefficient
	 */
	public synchronized void setP(double p) {
		m_P = p;
		
		if (table != null) {
			table.putNumber("p", p);
		}
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional and feed forward coefficients.
	 * 
	 * @param p
	 *            Proportional coefficient
	 * @param f
	 *            Feed forward coefficient
	 */
	public synchronized void setPF(double p, double f) {
		m_P = p;
		m_F = f;

		if (table != null) {
			table.putNumber("p", p);
			table.putNumber("f", f);
		}
	}

	/**
	 * Get the Proportional coefficient
	 * 
	 * @return proportional coefficient
	 */
	public synchronized double getP() {
		return m_P;
	}

	/**
	 * Get the Feed forward coefficient
	 * 
	 * @return feed forward coefficient
	 */
	public synchronized double getF() {
		return m_F;
	}

	/**
	 * Return the current PID result This is always centered on zero and
	 * constrained the the max and min outs
	 * 
	 * @return the latest calculated output
	 */
	public synchronized double get() {
		return m_result;
	}

	/**
	 * Set the setpoint for the PID
	 * 
	 * @param setpoint
	 *            the desired setpoint
	 */
	public synchronized void setSetpoint(double setpoint) {
		
		m_setpoint = setpoint;

		if (table != null) {
			table.putNumber("setpoint", m_setpoint);
		}
	}

	/**
	 * Returns the current setpoint of the PID
	 * 
	 * @return the current setpoint
	 */
	public synchronized double getSetpoint() {
		return m_setpoint;
	}

	/**
	 * Returns the current difference of the input from the setpoint
	 * 
	 * @return the current error
	 */
	public synchronized double getError() {
		return getSetpoint() - m_pidInput.pidGet();
	}

	/**
	 * Begin running the PID
	 */
	public synchronized void enable() {
		m_enabled = true;

		if (table != null) {
			table.putBoolean("enabled", true);
		}
	}

	/**
	 * Stop running the PID, this sets the output to zero before stopping.
	 */
	public synchronized void disable() {
		m_pidOutput.pidWrite(0);
		m_enabled = false;

		if (table != null) {
			table.putBoolean("enabled", false);
		}
	}

	/**
	 * Return true if PID is enabled.
	 */
	public synchronized boolean isEnable() {
		return m_enabled;
	}
	
	public void enableSmartDashboardPrinting(boolean willEnableP,boolean willEnableF, String subsystemName) {
		this.smartDashboardPrintingP = willEnableP;
		this.smartDashboardPrintingF = willEnableF;
		this.subsystemName  = subsystemName;
		return;
	}

	@Override
	public String getSmartDashboardType() {
		return "PIDController";
	}

	private final ITableListener listener = new ITableListener() {
		@Override
		public void valueChanged(ITable table, String key, Object value, boolean isNew) {
			if (key.equals("p") || key.equals("i") || key.equals("d") || key.equals("f")) {
				if (getP() != table.getNumber("p", 0.0) || getF() != table.getNumber("f", 0.0)) {
					setPF(table.getNumber("p", 0.0), table.getNumber("f", 0.0));
				}
			} else if (key.equals("setpoint")) {
				if (getSetpoint() != ((Double) value).doubleValue()) {
					setSetpoint(((Double) value).doubleValue());
				}
			} else if (key.equals("enabled")) {
				if (isEnable() != ((Boolean) value).booleanValue()) {
					if (((Boolean) value).booleanValue()) {
						enable();
					} else {
						disable();
					}
				}
			}
		}
	};
	private ITable table;

	@Override
	public void initTable(ITable table) {
		if (this.table != null) {
			this.table.removeTableListener(listener);
		}
		this.table = table;
		if (table != null) {
			table.putNumber("p", getP());
			table.putNumber("f", getF());
			table.putNumber("Current Output", get());
			table.putNumber("setpoint", getSetpoint());
			table.putBoolean("enabled", isEnable());
			table.addTableListener(listener, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITable getTable() {
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTable() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startLiveWindowMode() {
		disable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopLiveWindowMode() {
	}
}

package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 15:59
 */
public interface ResultsBrowser {
	/**
	 * Set and show new result
	 * @param result new result
	 */
	public void setResult(final DCIMetric result);

	/**
	 * Remove all results, reset to initial state
	 */
	public void reset();
}

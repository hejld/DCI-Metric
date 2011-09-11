package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 15:59
 */
public interface ResultsBrowser {
	public void setResult(final DCIMetric result);
	public void reset();
}

package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * ResultCalculation calculate DCI Metric result in background thread without blocking GUI.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 14:29
 */
class ResultCalculation extends SwingWorker<DCIMetric, Void> {

	private final Object[] wsdls;
	private final MainWindow window;

	/**
	 * ResultCalculation constructor.
	 * @param window result receiver object
	 * @param wsdls array of wsdl for metric calculation
	 */
	public ResultCalculation(MainWindow window, Object[] wsdls) {
		this.window = window;
		this.wsdls = wsdls;
	}

	/**
	 * Loads wsdl for metric calculation. Shows Error message on exception.
	 * @param metric Metric calculator which loads wsdl
	 * @param URI wsdl URI
	 */
	private void loadWsdl(DCIMetric metric, String URI) {
		try {
			metric.loadWsdl(URI);
		} catch (Exception ex) {
			Throwable cause = ex;
			while (cause.getCause() != null) {
				cause = cause.getCause();
			}
			JOptionPane.showMessageDialog(window, cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected DCIMetric doInBackground() throws Exception {
		DCIMetric metric = new DCIMetric();
		for (Object wsdl : wsdls) {
			File localFile = new File((String) wsdl);
			String URI;
			if (localFile.exists()) {
				URI = localFile.toURI().toString();
			} else {
				URI = (String)wsdl;
			}
			loadWsdl(metric, URI);
		}
		metric.computeMetric();
		return metric;
	}

	@Override
	protected void done() {
		DCIMetric metric = null;
		try {
			metric = get();
		} catch (InterruptedException ignored) {
			ignored.printStackTrace();
		} catch (ExecutionException ignored) {
			ignored.printStackTrace();
		}
		window.setResult(metric);
	}
}

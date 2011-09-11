package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 14:29
 */
class ResultCalculation extends SwingWorker<DCIMetric, Void> {

	private final Object[] wsdls;
	private final MainWindow window;

	public ResultCalculation(MainWindow window, Object[] wsdls) {
		this.window = window;
		this.wsdls = wsdls;
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

	private void loadWsdl(DCIMetric metric, String URI) {
		try {
			metric.loadWsdl(URI);
		} catch (Exception ignored) {
			JOptionPane.showMessageDialog(window, ignored.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
		}
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

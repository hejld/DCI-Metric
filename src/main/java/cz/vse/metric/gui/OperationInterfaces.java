package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 15:55
 */
public class OperationInterfaces extends JPanel implements ResultsBrowser {

	private JPanel SIPanels;
	private JScrollPane sp;

	/**
	 * Operation Interfaces panel constructor
	 */
	public OperationInterfaces() {
		init();
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		setLayout(new BorderLayout());
		SIPanels = new JPanel();
		SIPanels.setLayout(new BoxLayout(SIPanels, BoxLayout.PAGE_AXIS));
		sp = new JScrollPane(SIPanels);
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		sp.setBorder(null);
		add(sp);
	}

	/**
	 * Set result and update GUI
	 * @param result result
	 */
	public void setResult(DCIMetric result) {
		/* remove all old results */
		SIPanels.removeAll();
		/* create new panel for each result */
		for (ServiceInterface si : result.getServiceInterfaces()) {
			SIPanels.add(new OperationInterfacesPanel(si));
		}
		/* scroll sp to top*/
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sp.getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}

	/**
	 * Clear results
	 */
	public void reset() {
		SIPanels.removeAll();
	}
}

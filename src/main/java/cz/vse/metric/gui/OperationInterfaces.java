package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 15:55
 */
public class OperationInterfaces extends JPanel implements ResultsBrowser {

	private JPanel SIPanels;
	private JScrollPane sp;

	public OperationInterfaces() {
		init();
	}

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

	public void setResult(DCIMetric result) {
		SIPanels.removeAll();
		for (ServiceInterface si : result.getServiceInterfaces()) {
			SIPanels.add(new ServiceInterfacePanel(si));
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sp.getViewport().setViewPosition(new Point(0, 0));
			}
		});
		//sp.getViewport().setViewPosition(new Point(0,0));
	}

	public void reset() {
		SIPanels.removeAll();
	}
}

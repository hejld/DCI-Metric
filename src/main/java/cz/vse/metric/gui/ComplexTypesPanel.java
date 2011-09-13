package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.awt.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 15:55
 */
public class ComplexTypesPanel extends JPanel implements ResultsBrowser {

	private JPanel CTPanels;
	private JScrollPane sp;

	/**
	 * Complex Types result browser panel constructor
	 */
	public ComplexTypesPanel() {
		init();
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		setLayout(new BorderLayout());
		CTPanels = new JPanel();
		CTPanels.setLayout(new BoxLayout(CTPanels, BoxLayout.PAGE_AXIS));
		sp = new JScrollPane(CTPanels);
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
		/* remove all old result panels */
		CTPanels.removeAll();
		/* for each result create new panel */
		Map<QName, Integer> map = result.getComplexTypesWithUsageCounts();
		for(QName complexType : map.keySet()) {
			CTPanels.add(new ComplexTypesDetailPanel(complexType, map.get(complexType)));
		}
		/* scroll SP to top */
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
		CTPanels.removeAll();
	}
}

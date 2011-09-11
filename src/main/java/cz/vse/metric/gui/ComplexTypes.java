package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.awt.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 15:55
 */
public class ComplexTypes extends JPanel implements ResultsBrowser {

	private JPanel CTPanels;
	private JScrollPane sp;

	public ComplexTypes() {
		init();
	}

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

	public void setResult(DCIMetric result) {
		CTPanels.removeAll();
		Map<QName, Integer> map = result.getComplexTypesWithUsageCounts();
		for(QName complexType : map.keySet()) {
			CTPanels.add(new ComplexTypesPanel(complexType, map.get(complexType)));
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sp.getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}

	public void reset() {
		CTPanels.removeAll();
	}
}

package cz.vse.metric.gui;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.dci.ServiceInterfaceCombination;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.util.List;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 15:55
 */
public class OperationCombinations extends JPanel implements ResultsBrowser {

	private JPanel OCPanels;
	private JScrollPane sp;

	public OperationCombinations() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		OCPanels = new JPanel();
		OCPanels.setLayout(new BoxLayout(OCPanels, BoxLayout.PAGE_AXIS));
		sp = new JScrollPane(OCPanels);
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		sp.setBorder(null);
		add(sp);
	}

	public void setResult(DCIMetric result) {
		OCPanels.removeAll();

		List<ServiceInterfaceCombination> sicl = result.getServiceInterfaceCombinations();
		JPanel infoPanel = new JPanel();
		infoPanel.add(new JLabel("<html><b>Operation interface combinations (total: " + sicl.size() + " combinations)</b></html>"));
		OCPanels.add(infoPanel);

		for (ServiceInterfaceCombination sic : sicl) {
			OCPanels.add(new OperationCombinationsPanel(sic));
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sp.getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}

	public void reset() {
		OCPanels.removeAll();
	}
}

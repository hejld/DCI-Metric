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
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 15:55
 */
public class OperationCombinationsPanel extends JPanel implements ResultsBrowser {

	private JPanel OCPanels;
	private JScrollPane sp;

	/**
	 * Operation Combinations Panel constructor
	 */
	public OperationCombinationsPanel() {
		init();
	}

	/**
	 * Initialize GUI components
	 */
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

	/**
	 * Set result and update GUI
	 * @param result result
	 */
	public void setResult(DCIMetric result) {
		/* remove all old results */
		OCPanels.removeAll();

		/* Operation Combinations overview */
		List<ServiceInterfaceCombination> sicl = result.getServiceInterfaceCombinations();
		JPanel infoPanel = new JPanel();
		infoPanel.add(new JLabel("<html><b>Operation interface combinations (total: " + sicl.size() + " combinations)</b></html>"));
		OCPanels.add(infoPanel);

		/* create panel for each combination */
		for (ServiceInterfaceCombination sic : sicl) {
			OCPanels.add(new OperationCombinationsDetailPanel(sic));
		}
		/* scroll sp to top */
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
		OCPanels.removeAll();
	}
}

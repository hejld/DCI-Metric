package cz.vse.metric.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cz.vse.metric.dci.DCIMetric;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 15:16
 */
public class BasicResultsPanel extends JPanel implements ResultsBrowser {

	private JLabel DCI;
	private JLabel NDCI;
	private JLabel numberOfServiceOperations;
	private JLabel numberOfServiceCombinations;
	private JLabel averageNumberOfComplexTypesPerOperation;
	private JLabel numberOfComplexTypesThatWereReused;

	/**
	 * Basic Results Panel constructor
	 */
	public BasicResultsPanel() {
		init();

		FormLayout layout = new FormLayout("l:p:g, 3dlu, r:p:g",
				"p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");
		PanelBuilder pb = new PanelBuilder(layout, this);
		pb.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();
		CellConstraints lcc = new CellConstraints();
		pb.addLabel("DCI", cc.xy(1, 1), DCI, lcc.xy(3, 1));
		pb.addLabel("NDCI", cc.xy(1, 3), NDCI, lcc.xy(3, 3));
		pb.addSeparator("", cc.xyw(1, 5, 3));

		pb.addLabel("Total number of service operations", cc.xy(1, 7), numberOfServiceOperations, lcc.xy(3, 7));
		pb.addLabel("Total number of combinations (r)", cc.xy(1, 9), numberOfServiceCombinations, lcc.xy(3, 9));
		pb.addLabel(" ", cc.xy(1, 11));
		pb.addLabel("Average number of complex types per operation", cc.xy(1, 13), averageNumberOfComplexTypesPerOperation, lcc.xy(3, 13));
		pb.addLabel("Number of complex types that were reused", cc.xy(1, 15), numberOfComplexTypesThatWereReused, lcc.xy(3, 15));
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		DCI = new JLabel();
		NDCI = new JLabel();
		numberOfServiceOperations = new JLabel();
		numberOfServiceCombinations = new JLabel();
		averageNumberOfComplexTypesPerOperation = new JLabel();
		numberOfComplexTypesThatWereReused = new JLabel();
	}

	/**
	 * Set result and updates GUI
	 * @param result result
	 */
	public void setResult(final DCIMetric result) {
		DecimalFormat fformatter = new DecimalFormat("#,###.##");
		DecimalFormat iformatter = new DecimalFormat("#,###");

		/* set DCI only when is not NaN */
		if (!Float.valueOf(result.getDataCouplingIndex()).isNaN()) {
			DCI.setText(fformatter.format(result.getDataCouplingIndex()));
		}

		/* set NDCI only when is not NaN */
		if (!Float.valueOf(result.getNormalizedDataCouplingIndex()).isNaN()) {
			NDCI.setText(fformatter.format(result.getNormalizedDataCouplingIndex()));
		}
		
		numberOfServiceOperations.setText(iformatter.format(result.getServiceInterfaces().size()));
		numberOfServiceCombinations.setText(iformatter.format(result.getServiceInterfaceCombinations().size()/2));
		/* set average number of complex types per operation only when is not NaN */
		if (!Float.valueOf(result.getAverageNumberOfComplexElementsPerOperation()).isNaN()) {
			averageNumberOfComplexTypesPerOperation.setText(fformatter.format(result.getAverageNumberOfComplexElementsPerOperation()));
		}
		numberOfComplexTypesThatWereReused.setText(iformatter.format(result.getComplexTypesWithUsageCounts().size()));
	}

	/**
	 * Clear results
	 */
	public void reset() {
		DCI.setText(null);
		NDCI.setText(null);
		numberOfServiceOperations.setText(null);
		numberOfServiceCombinations.setText(null);
		averageNumberOfComplexTypesPerOperation.setText(null);
		numberOfComplexTypesThatWereReused.setText(null);
	}
}

package cz.vse.metric.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cz.vse.metric.dci.DCIMetric;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Window of DCI Metric GUI
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-10 19:04
 */
public class MainWindow extends JFrame implements MouseListener {

	private JButton addButton;
	private InputFieldWithFileChooser wsdlUriField;
	private LoadedDefinitionsPanel loadedDefinitions;
	private JButton resetButton;
	private JButton calculateButton;
	private JTabbedPane tabbedPane;
	private BasicResultsPanel basicResultsTab;
	private OperationInterfaces operationInterfacesTab;
	private ComplexTypesPanel complexTypesTab;
	private OperationCombinationsPanel operationCombinationsTab;
	private List<ResultsBrowser> resultBrowser;

	/**
	 * GUIs Main Window constructor
	 * @param appName application name shown in Window's title bar
	 */
	public MainWindow(final String appName) {
		super(appName);
		createGUI();
	}

	/**
	 * Creates GUI from initialized components
	 */
	private void createGUI() {
		init();

		FormLayout layout = new FormLayout("p, 3dlu, p:g, 3dlu, 20dlu, 60dlu",
				"p, 3dlu, f:70dlu, 3dlu, p, 3dlu, f:200dlu:g");
		PanelBuilder pb = new PanelBuilder(layout);
		pb.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		pb.addLabel("WSDL URL/File:", cc.xy(1, 1));
		pb.add(wsdlUriField, cc.xyw(3, 1, 3));
		pb.add(addButton, cc.xy(6, 1));
		pb.add(loadedDefinitions, cc.xyw(1, 3, 6));
		pb.add(resetButton, cc.xy(1, 5));
		pb.add(calculateButton, cc.xyw(5, 5, 2));
		pb.add(tabbedPane, cc.xyw(1, 7, 6));
		add(pb.getPanel());
		setMinimumSize(getPreferredSize());
		pack();
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		resultBrowser = new ArrayList<ResultsBrowser>();

		loadedDefinitions = new LoadedDefinitionsPanel();
		addButton = new JButton("Add");
		addButton.addMouseListener(this);

		resetButton = new JButton("Reset");
		resetButton.addMouseListener(this);

		calculateButton = new JButton("Calculate metric!");
		calculateButton.addMouseListener(this);

		tabbedPane = new JTabbedPane();

		basicResultsTab = new BasicResultsPanel();
		resultBrowser.add(basicResultsTab);
		tabbedPane.addTab("Basic Results", basicResultsTab);

		operationInterfacesTab = new OperationInterfaces();
		resultBrowser.add(operationInterfacesTab);
		tabbedPane.addTab("Operation Interfaces", operationInterfacesTab);

		complexTypesTab = new ComplexTypesPanel();
		resultBrowser.add(complexTypesTab);
		tabbedPane.addTab("Complex Types", complexTypesTab);

		operationCombinationsTab = new OperationCombinationsPanel();
		resultBrowser.add(operationCombinationsTab);
		tabbedPane.addTab("Operation Combinations", operationCombinationsTab);

		wsdlUriField = new InputFieldWithFileChooser(this);
	}

	/**
	 * Mouse clicked event handler.
	 * @param mouseEvent event to handle
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		Object source = mouseEvent.getSource();
		if (source == addButton) {
			/* add Button event handler */
			String URI = wsdlUriField.getText();
			URI = URI.trim();
			if (URI.length() > 0) {
				loadedDefinitions.add(URI);
			}
			wsdlUriField.setText(null);
		} else if (source == resetButton) {
			/* reset button event handler */
			loadedDefinitions.reset();
			wsdlUriField.setText(null);
			/* clear each result browser tab */
			for (ResultsBrowser result : resultBrowser) {
				result.reset();
			}
		} else if (source == calculateButton) {
			/* calculate button event handler */
			Object[] definitions = loadedDefinitions.getLoadedDefinitions();
			if (definitions.length > 0) {
				/* disable calculate button during calculation */
				calculateButton.setEnabled(false);
				/* set busy cursor */
				getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				/* start metric calculation */
				ResultCalculation calculator = new ResultCalculation(this, definitions);
				calculator.execute();
			}
		}
	}

	/**
	 * Set calculation result. Called from ResultCalculater after computation finished
	 * @param metric result
	 */
	public void setResult(DCIMetric metric) {
		/* enable calculate button */
		calculateButton.setEnabled(true);
		/* display result on each result browser tab */
		for (ResultsBrowser result : resultBrowser) {
			result.setResult(metric);
		}
		/* set normal cursor */
		getRootPane().setCursor(Cursor.getDefaultCursor());
	}

	public void mousePressed(MouseEvent mouseEvent) {}

	public void mouseReleased(MouseEvent mouseEvent) {}

	public void mouseEntered(MouseEvent mouseEvent) {}

	public void mouseExited(MouseEvent mouseEvent) {}
}

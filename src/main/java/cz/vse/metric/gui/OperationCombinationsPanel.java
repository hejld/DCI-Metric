package cz.vse.metric.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cz.vse.metric.dci.ServiceInterfaceCombination;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 16:06
 */
class OperationCombinationsPanel extends JPanel {

	private JLabel combination;
	private JLabel sharedElementsLabel;
	private JTextArea sharedElementsArea;
	private final ServiceInterfaceCombination sic;
	private static final DecimalFormat iformatter = new DecimalFormat("#");

	public OperationCombinationsPanel(ServiceInterfaceCombination sic) {
		this.sic = sic;
		init();

		FormLayout layout = new FormLayout("l:110dlu, 3dlu, l:200dlu:g",
				"t:p, 3dlu, t:p, 3dlu, p, 3dlu, p");
		PanelBuilder pb = new PanelBuilder(layout, this);
		pb.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();
		CellConstraints lcc = new CellConstraints();
		pb.addLabel("<html><b>Combination</b></html>", cc.xy(1, 1), combination, lcc.xy(3, 1));
		pb.addLabel("Shared elements", cc.xy(1, 3), sharedElementsLabel, lcc.xy(3, 3));
		pb.add(sharedElementsArea, cc.xy(3, 5));
		pb.addSeparator("", cc.xyw(1, 7, 3));
	}

	private void init() {
		setOpaque(false);
		combination = new JLabel();
		sharedElementsLabel = new JLabel();
		sharedElementsArea = new JTextArea();
		sharedElementsArea.setOpaque(false);
		sharedElementsArea.setBorder(null);

		combination.setText(sic.getInterfaceA().getServiceQName().getLocalPart() + " vs " + sic.getInterfaceB().getServiceQName().getLocalPart());
		sharedElementsLabel.setText(iformatter.format(sic.getNumberOfSharedElements()));
		sharedElementsArea.setText(getSharedElements(sic.getSharedElements()));
	}

	private String getSharedElements(List<QName> complexTypes) {
		StringBuilder sb = new StringBuilder();
		for (QName complexType : complexTypes) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(complexType.getLocalPart());
		}
		return sb.toString();
	}
}

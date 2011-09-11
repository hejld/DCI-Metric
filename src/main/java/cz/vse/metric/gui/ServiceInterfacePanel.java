package cz.vse.metric.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cz.vse.metric.dci.ServiceInterface;

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
class ServiceInterfacePanel extends JPanel {

	private JLabel serviceName;
	private JLabel numberOfComplexTypes;
	private JTextArea nonAnonymousComplexTypes;
	private JLabel numOfShared;
	private static final DecimalFormat iformatter = new DecimalFormat("#");
	private final ServiceInterface si;


	public ServiceInterfacePanel(ServiceInterface si) {
		this.si = si;
		init();

		FormLayout layout = new FormLayout("l:110dlu, 3dlu, l:200dlu:g",
				"t:p, 3dlu, t:p, 3dlu, t:p, 3dlu, t:p, 3dlu, p");
		PanelBuilder pb = new PanelBuilder(layout, this);
		pb.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();
		CellConstraints lcc = new CellConstraints();
		pb.addLabel("<html><b>Service name</b></html>", cc.xy(1, 1), serviceName, lcc.xy(3, 1));
		pb.addLabel("Number of distinct complex types", cc.xy(1, 3), numberOfComplexTypes, lcc.xy(3, 3));
		pb.addLabel("They are", cc.xy(1, 5), nonAnonymousComplexTypes, lcc.xy(3, 5, CellConstraints.FILL, CellConstraints.FILL));
		pb.addLabel("Shared", cc.xy(1, 7), numOfShared, lcc.xy(3, 7));
		pb.addSeparator("", cc.xyw(1, 9, 3));
	}

	private void init() {
		setOpaque(false);
		serviceName = new JLabel();
		numberOfComplexTypes = new JLabel();
		nonAnonymousComplexTypes = new JTextArea();
		nonAnonymousComplexTypes.setOpaque(false);
		nonAnonymousComplexTypes.setBorder(null);
		numOfShared = new JLabel();

		serviceName.setText(si.getServiceQName().toString());
		numberOfComplexTypes.setText(iformatter.format(si.getNumberOfComplexTypes()));
		nonAnonymousComplexTypes.setText(getNonAnonymousComplexTypesHTML(si.getNonAnonymousComplexTypes()));
		numOfShared.setText(iformatter.format(si.getSharedComplexTypeMap().size()));
	}

	private String getNonAnonymousComplexTypesHTML(List<QName> complexTypes) {
		StringBuilder sb = new StringBuilder();
		for (QName complexType : complexTypes) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(complexType.toString());
		}
		return sb.toString();
	}
}

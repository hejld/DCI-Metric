package cz.vse.metric.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 16:06
 */
class ComplexTypesDetailPanel extends JPanel {

	private JLabel complexType;
	private JLabel numberOfUsage;
	private final DecimalFormat iformatter = new DecimalFormat("#");
	private final QName name;
	private final int count;

	/**
	 * Complex Type Detail result panel
	 * @param name Complex Type Name
	 * @param count number of usage
	 */
	public ComplexTypesDetailPanel(QName name, int count) {
		this.name = name;
		this.count = count;
		init();

		FormLayout layout = new FormLayout("l:p, 3dlu, l:p:g",
				"p, 3dlu, p, 3dlu, p");
		PanelBuilder pb = new PanelBuilder(layout, this);
		pb.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();
		CellConstraints lcc = new CellConstraints();
		pb.addLabel("<html><b>Complex type</b></html>", cc.xy(1, 1), complexType, lcc.xy(3, 1));
		pb.addLabel("Number of distinct complex types", cc.xy(1, 3), numberOfUsage, lcc.xy(3, 3));
		pb.addSeparator("", cc.xyw(1, 5, 3));
	}

	/**
	 * Initialize components GUI
	 */
	private void init() {
		setOpaque(false);
		complexType = new JLabel();
		
		complexType = new JLabel();
		numberOfUsage = new JLabel();

		complexType.setText(name.toString());
		numberOfUsage.setText(iformatter.format(count));
	}
}

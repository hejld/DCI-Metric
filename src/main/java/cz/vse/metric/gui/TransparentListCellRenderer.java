package cz.vse.metric.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 13:44
 */
class TransparentListCellRenderer extends JLabel implements ListCellRenderer {
	public TransparentListCellRenderer() {
		setOpaque(false);
	}

	public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
		setText((String)o);
		return this;
	}
}

package cz.vse.metric.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This cell renderer does not render its background. As result you can see
 * parent component content/background.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 13:44
 */
class TransparentListCellRenderer extends JLabel implements ListCellRenderer {
	/**
	 * TransparentListCellRenderer constructor
	 */
	public TransparentListCellRenderer() {
		setOpaque(false);
	}

	/**
	 * returns Rendered Cell Component
	 * @param jList source list
	 * @param o object to render
	 * @param i cells index
	 * @param b is cell selected?
	 * @param b1 has cell focus?
	 * @return rendered component
	 */
	public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
		setText((String)o);
		return this;
	}
}

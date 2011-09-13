package cz.vse.metric.gui;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 01:09
 */
class LoadedDefinitionsPanel extends JPanel {
	private DefaultListModel listModel;

	/**
	 * Loaded Definitions Panel
	 */
	public LoadedDefinitionsPanel() {
		init();
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		setBorder(BorderFactory.createTitledBorder("Loaded Definitions"));
		setLayout(new BorderLayout());

		listModel = new DefaultListModel();

		JList loadedDefinitions = new JList();
		loadedDefinitions.setOpaque(false);
		loadedDefinitions.setModel(listModel);
		loadedDefinitions.setCellRenderer(new TransparentListCellRenderer());
		loadedDefinitions.setEnabled(false);

		JScrollPane sp = new JScrollPane(loadedDefinitions);
		sp.setBorder(null);
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		add(sp, BorderLayout.CENTER);
	}

	/**
	 * Add new wsdl location
	 * @param wsdl full filename/URI
	 */
	public void add(String wsdl) {
		if (!listModel.contains(wsdl)) {
			listModel.addElement(wsdl);
		}
	}

	/**
	 * Remove all elements
	 */
	public void reset() {
		listModel.removeAllElements();
	}

	/**
	 * Returns all elements in list
	 * @return Object[] with elements
	 */
	public Object[] getLoadedDefinitions() {
		return listModel.toArray();
	}

}

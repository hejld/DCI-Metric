package cz.vse.metric.gui;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-11
 * Time: 01:09
 */
class LoadedDefinitionsPanel extends JPanel {
	private DefaultListModel listModel;

	public LoadedDefinitionsPanel() {
		init();
	}

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

	public void add(String wsdl) {
		if (!listModel.contains(wsdl)) {
			listModel.addElement(wsdl);
		}
	}

	public void reset() {
		listModel.removeAllElements();
	}

	public Object[] getLoadedDefinitions() {
		return listModel.toArray();
	}

}

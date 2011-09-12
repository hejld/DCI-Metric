package cz.vse.metric.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-11 24:02
 */
class InputFieldWithFileChooser extends JPanel implements MouseListener, FilenameFilter {

	private JTextField textField;
	private JButton fileChooserButton;
	private FileDialog fileDialog;
	private final Frame owner;

	/**
	 * Combined text field with button that opens file dialog.
	 * @param owner file dialog owner
	 */
	public InputFieldWithFileChooser(Frame owner) {
		this.owner = owner;
		init();
		setLayout(new BorderLayout());
		add(textField, BorderLayout.CENTER);
		add(fileChooserButton, BorderLayout.EAST);
	}

	/**
	 * Initialize GUI components
	 */
	private void init() {
		LookAndFeel.installBorder(this, "TextField.border");
		fileDialog = new FileDialog(owner);
		fileDialog.setFilenameFilter(this);
		fileDialog.setMode(FileDialog.LOAD);

		textField = new JTextField(40);
		Insets textFieldInsets = textField.getInsets();
		textField.setBorder(null);

		Dimension size = textField.getPreferredSize();
		size.setSize(size.getWidth()+textFieldInsets.left, size.getHeight()+textFieldInsets.top);
		int height = size.height;
		setPreferredSize(size);

		fileChooserButton = new JButton(getFolderIcon(height));
		fileChooserButton.setMargin(new Insets(0,0,0,0));
		fileChooserButton.setPreferredSize(new Dimension(height, height));
		fileChooserButton.addMouseListener(this);
		fileChooserButton.setBorder(null);

		setBackground(textField.getBackground());
	}

	/**
	 * Loads button icon from file
	 * @param height icon size - height x height
	 * @return loaded icon
	 */
	private ImageIcon getFolderIcon(int height) {
		URL iconURL = InputFieldWithFileChooser.class.getResource("folder.png");
		ImageIcon icon = new ImageIcon(iconURL);
		Image image = icon.getImage();
		image = image.getScaledInstance(height, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	/**
	 * Returns text field text
	 * @return String
	 */
	public String getText() {
		return textField.getText();
	}

	/**
	 * Set input field text
	 * @param text new input field content
	 */
	public void setText(String text) {
		textField.setText(text);
	}

	/**
	 * Mouse listener
	 * @param mouseEvent mouse event
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		/* handle fileChooserButton event */
		if (mouseEvent.getSource() == fileChooserButton) {
			/* show file dialog */
			fileDialog.setVisible(true);
			if (fileDialog.getFile() != null) {
				/* get selected file */
				setText(fileDialog.getDirectory() + fileDialog.getFile());
			}
		}
	}

	public void mousePressed(MouseEvent mouseEvent) {}

	public void mouseReleased(MouseEvent mouseEvent) {}

	public void mouseEntered(MouseEvent mouseEvent) {}

	public void mouseExited(MouseEvent mouseEvent) {}

	/**
	 * Check file extension. Accepts only .wsdl
	 * @param file directory
	 * @param s filename
	 * @return true if filename ends with .wsdl, otherwise false
	 */
	public boolean accept(File file, String s) {
		return s.endsWith(".wsdl");
	}
}

package cz.vse.metric;

import cz.vse.metric.gui.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:djaara83@gmail.com">Jaroslav Barton</a>
 * Date: 2011-09-10 19:08
 */
public class GUIApplication implements UserInterface {

	private final MainWindow mainWindow;
	private static final String APP_NAME = "DCI Metric GUI";

	/**
	 * Constructor of GUI for DCIMetric
	 */
	public GUIApplication() {
		/*
		 * for Mac OS X set application name that will be shown in menu bar
		 * and load dock icon
		 */
		if (System.getProperty("os.name").startsWith("Mac OS X")) {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);

			URL iconURL = GUIApplication.class.getResource("gui/ico.png");
			try {
				Image image = ImageIO.read(iconURL);
				com.apple.eawt.Application macApp = com.apple.eawt.Application.getApplication();
				macApp.setDockIconImage(image);
			} catch (IOException ignored) {}
		} else {
			/*
			 * for other operating system set system L&F
			 */
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException ignored) {
			} catch (InstantiationException ignored) {
			} catch (IllegalAccessException ignored) {
			} catch (UnsupportedLookAndFeelException ignored) {
			}
		}
		/* create GUI's Main Window */
		mainWindow = new MainWindow(APP_NAME);
	}

	/**
	 * Start DCI Metric GUI
	 */
	public void start() {
		mainWindow.setVisible(true);
	}
}

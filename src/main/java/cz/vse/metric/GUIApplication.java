package cz.vse.metric;

import cz.vse.metric.dci.DCIMetric;
import cz.vse.metric.gui.MainWindow;

import javax.swing.*;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: djaara
 * Date: 2011-09-10
 * Time: 19:08
 */
public class GUIApplication implements UserInterface {

	private final MainWindow mainWindow;

	public GUIApplication() {
		if (System.getProperty("os.name").startsWith("Mac OS X")) {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DCI Metric");
		}
		mainWindow = new MainWindow();
	}

	public void start() {
		mainWindow.setVisible(true);
	}
}

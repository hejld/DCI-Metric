package cz.vse.metric;

/**
 * Class Application
 * Main class of the tool
 *
 * @author <a href="mailto:daniel.hejl@hotmail.com">Daniel Hejl</a>
 */
public class Application {

    public static void main(String[] args) {
		UserInterface app;
		if (args.length == 0 || !"-cli".equals(args[0])) {
			app = new GUIApplication();
		} else {
			app = new ConsoleApplication();
		}

		app.start();
	}
}

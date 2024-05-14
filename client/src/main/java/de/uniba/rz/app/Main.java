package de.uniba.rz.app;

import de.uniba.rz.app.AmpqImplementation.AmpqTicketManagementBackend;
import de.uniba.rz.app.gRPC.gRPCticketManagementBackend;
import de.uniba.rz.restful.restTicketbackendManagement;
import de.uniba.rz.ui.swing.MainFrame;
import de.uniba.rz.ui.swing.SwingMainController;
import de.uniba.rz.ui.swing.SwingMainModel;

/**
 * Main class to start the TicketManagement5000 client application Currently
 * only a local backend implementation is registered.<br>
 * 
 * To add additional implementations modify the method
 * <code>evaluateArgs(String[] args)</code>
 *
 * @see #evaluateArgs(String[])
 */
public class Main {

	/**
	 * Starts the TicketManagement5000 application based on the given arguments
	 * 
	 * <p>
	 * <b>TODO No changes needed here - but documentation of allowed args should
	 * be updated</b>
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TicketManagementBackend backendToUse = evaluateArgs(args);

		SwingMainController control = new SwingMainController(backendToUse);
		SwingMainModel model = new SwingMainModel(backendToUse);
		MainFrame mf = new MainFrame(control, model);

		control.setMainFrame(mf);
		control.setSwingMainModel(model);

		control.start();
	}

	/**
	 * Determines which {@link TicketManagementBackend} should be used by
	 * evaluating the given {@code args}.
	 * 
	 * If <code>null</code>, an empty array or an unknown argument String is
	 * passed, the default {@code LocalTicketManagementBackend} is used.
	 * 
	 * <p>
	 * <b>This method must be modified in order to register new implementations
	 * of {@code TicketManagementBackend}.</b>
	 * </p>
	 * 
	 * @param args
	 *            a String array to be evaluated
	 * @return the selected {@link TicketManagementBackend} implementation
	 * 
	 * @see TicketManagementBackend
	 */
	private static TicketManagementBackend evaluateArgs(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("No arguments passed. Using local backend implementation.");
			return new LocalTicketManagementBackend();
		} else {
			switch (args[0]) {
				case "udp":
					System.out.println("Using UDP backend implemenation.");
					return new UDPTicketManagementBackend();
				case "ampq":
					System.out.println("Using Ampq backend implemenation.");
					return new AmpqTicketMangamentBackend(args[1], args[2]);
				case "grpc":
					System.out.println("Using gRPC backend implemenation.");
					int port = Integer.parseInt(args[2]);
					return new gRPCticketManagmentBackend(args[1], port);
				case "rest":
					System.out.println("Using Rest backend implemenation.");
					return new restTicketbackendManagement();
				default:
					System.out.println("Unknown Argument. Using UDP backend implemenation.");
					return new UDPTicketManagementBackend();
			}
		}
	}
}

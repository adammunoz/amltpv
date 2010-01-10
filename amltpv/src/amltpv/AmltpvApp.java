/*
 * AmltpvApp.java
 */

package amltpv;

import java.awt.SplashScreen;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AmltpvApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new AmltpvView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of AmltpvApp
     */
    public static AmltpvApp getApplication() {
        return Application.getInstance(AmltpvApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
//        SplashScreen splash = SplashScreen.getSplashScreen();
//        if(splash==null)
//            AmltpvView.util.log("It was null!!");
//        else
//            AmltpvView.util.log("It wasn't null!!!");
        launch(AmltpvApp.class, args);
    }
}

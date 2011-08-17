package speechtimer;


//import java.awt.*;
//import javax.swing.*;

import java.awt.event.*;
import javax.swing.Timer; // not java.util.Timer


/**
* An applet that counts down from a specified time. When it reaches 00:00,
* it optionally plays a sound and optionally moves the browser to a new page.
* Place the mouse over the applet to pause the count; move it off to resume.
* This class demonstrates most applet methods and features.
**/
public class Countdown {

    //vars
    private int seconds = 60;
    private int minutes = 60;
    static Timer mainCountDown;

    //constructor
    public Countdown () {
        mainCountDown = new Timer(1000, null);
        mainCountDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                  if (seconds <= 0) {
                       minutes--;
                       seconds = 60;
                  }

                  System.out.println(minutes + ":" + seconds + "min");
                  seconds--;
                  WindowContainer.countdownLabelClockMin.setText(Integer.toString(minutes));
                  WindowContainer.countdownLabelClockSec.setText(Integer.toString(seconds));
          }
       });
    }

    public static void startMainCountDown () {
        mainCountDown.start();
    }

    public static void stopMainCountDown () {
        mainCountDown.stop();
    }
}
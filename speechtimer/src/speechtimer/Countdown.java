package speechtimer;

import java.awt.event.*;
import javax.swing.Timer; // not java.util.Timer for thread safety
import java.awt.Color;
import java.util.*;

public class Countdown {

    //vars
    private int seconds;
    private int minutes;
    static javax.swing.Timer mainCountDown;
    private Timer errorLabelTimer;

    //constructor
    public Countdown () {
        
        // cast current time object from array list to integer
        String minutesObjToString = WindowContainer.speakerIndexMin.get(WindowContainer.selectedIndex).toString();
        String secondsObjToString = WindowContainer.speakerIndexSec.get(WindowContainer.selectedIndex).toString();
        minutes = Integer.parseInt(minutesObjToString);
        seconds = Integer.parseInt(secondsObjToString);

        // countdown logic
        mainCountDown = new javax.swing.Timer(1000, null);
        mainCountDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // see weather we got minutes left
                if (minutes > -1) {

                    if (seconds <= 0) {
                        minutes--;
                        seconds = 60;
                    }
                  
                    System.out.println(minutes + ":" + seconds + "min");
                    seconds--;
                    WindowContainer.countdownLabelClockMin.setText(Integer.toString(minutes));
                    WindowContainer.countdownLabelClockSec.setText(Integer.toString(seconds));
                }
                // game over..
                else {
                    mainCountDown.stop();
                    WindowContainer.countdownLabelClockMin.setText("0");
                    WindowContainer.countdownLabelClockSec.setText("0");
                    Color darkRed = new Color(178,34,34);
                    WindowContainer.errorHandlingLabel.setForeground(darkRed);
                    WindowContainer.errorHandlingLabel.setText("Thank you! Your speech time is over.");

                    // let the message blink
                    errorLabelTimer = new Timer(20000, null);
                    errorLabelTimer.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                             WindowContainer.errorHandlingLabel.setText("");
                             errorLabelTimer.stop();
                        }
                    });
                    errorLabelTimer.start();
                }
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
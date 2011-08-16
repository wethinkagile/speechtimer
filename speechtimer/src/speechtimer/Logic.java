/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speechtimer;

/**
 *
 * @author legolas
 */

import javax.swing.*;

public class Logic {

    Countdown cd;
    private int minutes;

    //constructor
    public Logic () {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        // TO DO: Put event listeners for start and stop buttons here

        cd = new Countdown();
        cd.startMainCountDown();

        // 1) If Speaker names are clicked on preview pane, create new countdown instance,
        // 2) also check if speaker is alrdy in array with previous used time

        // Load Txt File into Textbox

        // 1) If name in txt file is clicked display change names in preview pane AND create new cd instance
        // 2) also check if speaker is alrdy in array with previous used time

        // If new speaker is selected, store speaker and time in array
        


    }

    private static void createAndShowGUI() {
        WindowContainer wc = new WindowContainer();
        wc.setVisible(true);
    }
}

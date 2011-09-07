package speechtimer;


import javax.swing.*;

/**
 *
 * @author Stephan Kristyn
 */
public class Main {

    private static void createAndShowGUI() {
        WindowContainer wc = new WindowContainer(15);
        wc.setVisible(true);
    }
   
    public static void main(String[] args) {
        // Run Graphical User Interface
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
package speechtimer;


import javax.swing.*;

/**
 *
 * @author Stephan Kristyn
 */
public class Main {

    private static void createAndShowGUI() {
        WindowContainer wc = new WindowContainer();
        wc.setVisible(true);
    }
   
    public static void main(String[] args) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
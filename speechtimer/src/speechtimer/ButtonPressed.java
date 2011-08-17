/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speechtimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author legolas
 */

    // helper classes
    public class ButtonPressed implements ActionListener {

       private String clickedItem;
       private JPanel parent;
       private Countdown cd;
       boolean exists;
       private ArrayList linesArray = new ArrayList();
       private int i;
       private final String newline = "\n";

       public ButtonPressed (String clickedItem, JPanel parent) {
           this.clickedItem = clickedItem;
           this.parent = parent;
           cd = new Countdown();
       }

       public void actionPerformed (ActionEvent e) {
            if (clickedItem.equals("start")){
                cd.startMainCountDown();
            }

            else if (clickedItem.equals("stop")) {
                cd.stopMainCountDown();
            }

            else if (clickedItem.equals("browse")) {

                // Create a file chooser
                final JFileChooser fc = new JFileChooser();
                
                // In response to a button click:
                int returnVal = fc.showOpenDialog(parent);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    
                    // Retreive File
                    File selectedFile = fc.getSelectedFile();
                    String pathAndFile = selectedFile.getParent() + "/" + selectedFile.getName();
                    
                    // Change GUI
                    WindowContainer.browseToFileField.setText(pathAndFile);

                    // Load list into ArrayList
                    File file=new File(pathAndFile);
                    exists = file.exists();
                    if (!exists) {
                        System.out.println("Something is wrong with the file.");
                    }

                    try {
                        FileInputStream fstream =
                        new FileInputStream(pathAndFile);
                        DataInputStream in = new DataInputStream(fstream);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String strLine;

                        while ((strLine = br.readLine()) != null)   {
                            // debug
                            // System.out.println (strLine);
                            linesArray.add(strLine);
                            i++;
                        }
                        in.close();
                    }

                    catch (Exception ioerror){
                        System.err.println("Error: " + ioerror.getMessage());
                    }

                    // Load ArrayList into GUI
                    Iterator iterator = linesArray.iterator();
                    while(iterator.hasNext()) {
                        // debug
                        // System.out.println(iterator.next().toString());
                        WindowContainer.speakerList.append(iterator.next().toString() + newline);
                        WindowContainer.speakerList.setCaretPosition(WindowContainer.speakerList.getDocument().getLength());
                    }
                }
                    

                else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    System.out.println("canceled");
                }
        }
    }
}


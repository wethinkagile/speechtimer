/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package speechtimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.*;

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
       private int labelFadeDelta = 1650;

       // default time values
       private int myMinutes = 14;
       private int mySeconds = 59;
       
       // error handling countdown
       static boolean countdownRunning = false;

       // error handling label timer
       private Timer errorLabelTimer;
       private boolean labelFadeTimer = false;


       // constructor
       public ButtonPressed (String clickedItem, JPanel parent) {
           this.clickedItem = clickedItem;
           this.parent = parent;
       }

       public void actionPerformed (ActionEvent e) {
            if (clickedItem.equals("start")){
                // error handling
                if (!countdownRunning) {
                    cd = new Countdown();
                    cd.startMainCountDown();
                    countdownRunning = true;

                    // UI Feedback / Usability
                    Color customColor = new Color(4,139,34);
                    WindowContainer.errorHandlingLabel.setForeground(customColor);
                    WindowContainer.errorHandlingLabel.setText("Starting Timer.");

                    if (!labelFadeTimer) {
                        // let the error message disappear again
                        errorLabelTimer = new Timer(labelFadeDelta, null);
                        errorLabelTimer.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 WindowContainer.errorHandlingLabel.setText("");
                                 errorLabelTimer.stop();
                                 labelFadeTimer = false;
                          }
                       });
                       errorLabelTimer.start();
                       labelFadeTimer = true;
                    }
                }

                // UI Feedback / Usability
                else {
                    Color customColor = new Color(178,34,34);
                    WindowContainer.errorHandlingLabel.setForeground(customColor);
                    WindowContainer.errorHandlingLabel.setText("You clicked Start twice.");
                        
                    if (!labelFadeTimer) {
                        // let the error message disappear again
                        errorLabelTimer = new Timer(labelFadeDelta, null);
                        errorLabelTimer.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 WindowContainer.errorHandlingLabel.setText("");
                                 errorLabelTimer.stop();
                                 labelFadeTimer = false;
                          }
                       });
                       errorLabelTimer.start();
                       labelFadeTimer = true;
                    }
                }
            }

            else if (clickedItem.equals("stop")) {
                cd.stopMainCountDown();
                // countdownTimer error Handling
                countdownRunning = false;

                // UI Feedback / Usability
                Color customColor = new Color(4,139,34);
                WindowContainer.errorHandlingLabel.setForeground(customColor);
                WindowContainer.errorHandlingLabel.setText("Stopped.");

                if (!labelFadeTimer) {
                    // let the error message disappear again
                    errorLabelTimer = new Timer(labelFadeDelta, null);
                    errorLabelTimer.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                             WindowContainer.errorHandlingLabel.setText("");
                             errorLabelTimer.stop();
                             labelFadeTimer = false;
                      }
                   });
                   errorLabelTimer.start();
                   labelFadeTimer = true;
                }

                // If Stop is clicked save index/time pair in arraylist
                // Writing Index and its time to the Arraylist speakerIndex
                WindowContainer.speakerIndexMin.set(WindowContainer.selectedIndex, WindowContainer.countdownLabelClockMin.getText());
                WindowContainer.speakerIndexSec.set(WindowContainer.selectedIndex, WindowContainer.countdownLabelClockSec.getText());
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

                            // Filling index of read speaker and a default time to the Arraylist speakerIndex
                            WindowContainer.speakerIndexMin.add(i,myMinutes);
                            WindowContainer.speakerIndexSec.add(i,mySeconds);
                            i++;
                        }
                        in.close();
                    }

                    catch (Exception ioerror){
                        System.err.println("Error: " + ioerror.getMessage());
                    }

                    // Load linesArray into GUI
                    Iterator iterator = linesArray.iterator();
                    while(iterator.hasNext()) {
                        WindowContainer.listModel.addElement(iterator.next().toString() + newline);
                    }
                }
                    

                else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    System.out.println("canceled");
                }
        }
    }
}


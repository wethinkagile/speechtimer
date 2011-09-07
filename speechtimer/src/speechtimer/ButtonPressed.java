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
import javax.swing.Timer;
import java.awt.*;

/**
 *
 * @author nottinhill
 * A lot of stuff happens in here.. 
 */

    // helper classes
    public class ButtonPressed implements ActionListener {

       private String clickedItem;
       private JPanel parent;
       private Countdown cd;
       boolean exists;
       private ArrayList<String> linesArray = new ArrayList<String>();
       private int i;
       private int z;
       private final String newline = "\n";
       private int labelFadeDelta = 1650;
       
       Color darkGreen = new Color(4,139,34);
       Color darkRed = new Color(178,34,34);

       // default time values
       static int myMinutes = 15;
       private int mySeconds = 0;
       
       // error handling countdown
       static boolean countdownRunning = false;

       // start/stop error handling label timer LEFT
       private Timer errorLabelTimer;
       private boolean labelFadeTimer = false;
       
       // set timer error handling label timer MIDDLE
       private Timer errorLabelTimer2;
       private boolean labelFadeTimer2 = false;
       
           // set timer error handling label timer RIGHT
       private Timer errorLabelTimer3;
       private boolean labelFadeTimer3 = false;


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
                    WindowContainer.errorHandlingLabel.setForeground(darkGreen);
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
                    WindowContainer.errorHandlingLabel.setForeground(darkRed);
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
                WindowContainer.errorHandlingLabel.setForeground(darkGreen);
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

                /*
                 * If Stop is clicked save index/time pair in arraylist
                 * Writing Index and its time to the Arraylist speakerIndex
                 */
                WindowContainer.speakerIndexMin.set(WindowContainer.selectedIndex, Integer.parseInt(WindowContainer.countdownLabelClockMin.getText()));
                WindowContainer.speakerIndexSec.set(WindowContainer.selectedIndex, Integer.parseInt(WindowContainer.countdownLabelClockSec.getText()));
            }

            else if (clickedItem.equals("setTime")) {
                              
                // error handling
                if (!countdownRunning) {
                
                    String input = WindowContainer.editSpeechTimeField.getText();
                    
                    // check weather the correct format is entered
                    try {
                        // if this works it won't fall through to the catch phrase
                        int x = Integer.parseInt(input);

                        // debug
                        System.out.println("This number was entered: " + x);
                        
                        // check weather the whole number is positive
                        if (x > 0) {
                            
                            // change ALL speakers to new time
                            if (WindowContainer.editSpeechCheckBox.isSelected()){
                                
                                int userEnteredTime = Integer.parseInt(WindowContainer.editSpeechTimeField.getText());
                                Iterator setTimeIterator = WindowContainer.speakerIndexMin.iterator();
                                z = 0;
                                while(setTimeIterator.hasNext()) {
                                    WindowContainer.speakerIndexMin.set(z,userEnteredTime);
                                    WindowContainer.speakerIndexSec.set(z,0);
                                    System.out.println(setTimeIterator.next());
                                    z++;
                                   
                                }
                                // also update the GUI
                                WindowContainer.countdownLabelClockMin.setText(WindowContainer.editSpeechTimeField.getText()); 
                                WindowContainer.countdownLabelClockSec.setText("0");
                                
                                // also update instance var myMinutes, for addSpeaker plus-button to use new time
                                myMinutes = userEnteredTime;
                                 // Usability: Let the user know that he changed time for all talkers
                                WindowContainer.errorHandlingLabelRight.setForeground(darkGreen);
                                WindowContainer.errorHandlingLabelRight.setText("All talker now have: " + x + " minutes.");

                                if (!labelFadeTimer2) {
                                    // let the error message disappear again
                                    errorLabelTimer2 = new Timer(labelFadeDelta, null);
                                    errorLabelTimer2.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                             WindowContainer.errorHandlingLabelRight.setText("");
                                             // reset checkbox for User Error Prevention
                                             WindowContainer.editSpeechCheckBox.setSelected(false);
                                             errorLabelTimer2.stop();
                                             labelFadeTimer2 = false;
                                      }
                                   });
                                errorLabelTimer2.start();
                                labelFadeTimer2 = true;
                                }
                            }
                            
                            else { // just change ONE speaker to new time
                               
                                /* get time from editable textfield after set time 
                                 * is clicked and store into according array index 
                                 */
                                int userEnteredTime = Integer.parseInt(WindowContainer.editSpeechTimeField.getText());
                                WindowContainer.speakerIndexMin.set(WindowContainer.selectedIndex, userEnteredTime);
                                WindowContainer.speakerIndexSec.set(WindowContainer.selectedIndex, 0);
                                
                                // also update the GUI
                                WindowContainer.countdownLabelClockMin.setText(WindowContainer.editSpeechTimeField.getText());
                                WindowContainer.countdownLabelClockSec.setText("0");

                                // User Feedback
                                String currentTalker = WindowContainer.currentTalker.getText();
                                WindowContainer.errorHandlingLabelRight.setForeground(darkGreen);
                                WindowContainer.errorHandlingLabelRight.setText(currentTalker + ": " + x + " minutes.");

                                if (!labelFadeTimer2) {
                                    // let the error message disappear again
                                    errorLabelTimer2 = new Timer(labelFadeDelta, null);
                                    errorLabelTimer2.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                             WindowContainer.errorHandlingLabelRight.setText("");
                                             errorLabelTimer2.stop();
                                             labelFadeTimer2 = false;
                                      }
                                   });
                                errorLabelTimer2.start();
                                labelFadeTimer2 = true;
                                }
                            }
                        }
                        else { // fall through if time entered isn't a positive value
                             // debug
                             System.out.println("error: negative whole number entered");
                             // error handling
                             WindowContainer.errorHandlingLabelRight.setForeground(darkRed);
                             WindowContainer.errorHandlingLabelRight.setText("Please enter a positive value!");
                            if (!labelFadeTimer2) {
                                 // let the error message disappear again
                                 errorLabelTimer2 = new Timer(labelFadeDelta, null);
                                 errorLabelTimer2.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         WindowContainer.errorHandlingLabelRight.setText("");
                                         errorLabelTimer2.stop();
                                         labelFadeTimer2 = false;
                                     }
                                 });
                                errorLabelTimer2.start();
                                labelFadeTimer2 = true;
                            }
                        }
                    }

                    catch (NumberFormatException nFE) {
                         // debug
                         System.out.println("error: not a whole numbers was entered!");
                         // error handling
                         WindowContainer.errorHandlingLabelRight.setForeground(darkRed);
                         WindowContainer.errorHandlingLabelRight.setText("Not a whole number was entered!");

                         if (!labelFadeTimer2) {
                             // let the error message disappear again
                             errorLabelTimer2 = new Timer(labelFadeDelta, null);
                             errorLabelTimer2.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                     WindowContainer.errorHandlingLabelRight.setText("");
                                     errorLabelTimer2.stop();
                                     labelFadeTimer2 = false;
                               }
                            });
                        errorLabelTimer2.start();
                        labelFadeTimer2 = true;
                        }
                    } 
                }
                
                else {
                    WindowContainer.errorHandlingLabelRight.setForeground(darkRed);
                    WindowContainer.errorHandlingLabelRight.setText("Please stop the countdown first!");
                    // reset checkbox for error prevention (we dont care weather it was checked or not)
                    WindowContainer.editSpeechCheckBox.setSelected(false);

                    if (!labelFadeTimer2) {
                        // let the error message disappear again
                        errorLabelTimer2 = new Timer(labelFadeDelta, null);
                        errorLabelTimer2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 WindowContainer.errorHandlingLabelRight.setText("");
                                 errorLabelTimer2.stop();
                                 labelFadeTimer2 = false;
                          }
                       });
                    errorLabelTimer2.start();
                    labelFadeTimer2 = true;
                    }
                }
            }
            
            else if (clickedItem.equals("addSpeaker")) {
                // error handling
                if (!countdownRunning) {
                    
                    // avoid empty addRemoveSpeaker field
                    if (WindowContainer.addRemoveSpeakerField.getText().length() != 0){

                        // retreive
                        String aNewSpeaker = WindowContainer.addRemoveSpeakerField.getText();
                        // extend
                        WindowContainer.listModel.addElement(aNewSpeaker);

                        WindowContainer.speakerIndexMin.add(myMinutes);
                        WindowContainer.speakerIndexSec.add(mySeconds);
                    }
                    else {
                        // User Feedback 
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setForeground(darkRed);
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("You did not provide a name!");

                        if (!labelFadeTimer3) {
                            // let the error message disappear again
                            errorLabelTimer3 = new Timer(labelFadeDelta, null);
                            errorLabelTimer3.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                     WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("");
                                     errorLabelTimer3.stop();
                                     labelFadeTimer3 = false;
                              }
                           });
                           errorLabelTimer3.start();
                           labelFadeTimer3 = true;
                        }
                    }
                }
                
                else {
                     // UI Feedback / Usability
                    WindowContainer.addRemoveSpeakerFieldErrorLabel.setForeground(darkRed);
                    WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("Stop the countdown first!");

                    if (!labelFadeTimer3) {
                        // let the error message disappear again
                        errorLabelTimer3 = new Timer(labelFadeDelta, null);
                        errorLabelTimer3.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("");
                                 errorLabelTimer3.stop();
                                 labelFadeTimer3 = false;
                          }
                       });
                       errorLabelTimer3.start();
                       labelFadeTimer3 = true;
                    }
                }
            }
            
            else if (clickedItem.equals("removeSpeaker")) {
                
                 // error handling
                if (!countdownRunning) {
                    // avoid empty addRemoveSpeaker field
                    if (WindowContainer.addRemoveSpeakerField.getText().length() != 0){
                        // store for user feedback use before removing
                        String currentTalker = WindowContainer.currentTalker.getText();

                        // remove from list 
                        WindowContainer.listModel.remove(WindowContainer.selectedIndex); 
                        // clear text field
                        WindowContainer.addRemoveSpeakerField.setText("");
                        // clear speaker label
                        WindowContainer.currentTalker.setText("");
                        // remove from min and sec list
                        WindowContainer.speakerIndexMin.remove(WindowContainer.selectedIndex);
                        WindowContainer.speakerIndexSec.remove(WindowContainer.selectedIndex);

                        // User Feedback 
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setForeground(darkGreen);
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("You removed " + currentTalker);

                        if (!labelFadeTimer3) {
                            // let the error message disappear again
                            errorLabelTimer3 = new Timer(labelFadeDelta, null);
                            errorLabelTimer3.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                     WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("");
                                     errorLabelTimer3.stop();
                                     labelFadeTimer3 = false;
                              }
                           });
                           errorLabelTimer3.start();
                           labelFadeTimer3 = true;
                        }
                    }
                    else {
                        // User Feedback 
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setForeground(darkRed);
                        WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("You did not provide a name!");

                        if (!labelFadeTimer3) {
                            // let the error message disappear again
                            errorLabelTimer3 = new Timer(labelFadeDelta, null);
                            errorLabelTimer3.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                     WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("");
                                     errorLabelTimer3.stop();
                                     labelFadeTimer3 = false;
                              }
                           });
                           errorLabelTimer3.start();
                           labelFadeTimer3 = true;
                        }
                    }
                }
                
                else {
                    // UI Feedback / Usability
                    WindowContainer.addRemoveSpeakerFieldErrorLabel.setForeground(darkRed);
                    WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("Stop the countdown first!");

                    if (!labelFadeTimer3) {
                        // let the error message disappear again
                        errorLabelTimer3 = new Timer(labelFadeDelta, null);
                        errorLabelTimer3.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 WindowContainer.addRemoveSpeakerFieldErrorLabel.setText("");
                                 errorLabelTimer3.stop();
                                 labelFadeTimer3 = false;
                          }
                       });
                       errorLabelTimer3.start();
                       labelFadeTimer3 = true;
                    }
                }
            }
            
            else if (clickedItem.equals("toggleClock")) {
                
                if (WindowContainer.countdownLabelClockMin.isVisible()) {
                    
                    WindowContainer.countdownLabelClockMin.setVisible(false);
                    WindowContainer.countdownLabelMin.setVisible(false);
                    WindowContainer.countdownLabelClockSec.setVisible(false);
                    WindowContainer.countdownLabelSec.setVisible(false);
                }   
                
                else {
                    
                    WindowContainer.countdownLabelClockMin.setVisible(true);
                    WindowContainer.countdownLabelMin.setVisible(true);
                    WindowContainer.countdownLabelClockSec.setVisible(true);
                    WindowContainer.countdownLabelSec.setVisible(true);
                }
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


package speechtimer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

/**
 *
 * @author nottinhill
 */
public class WindowContainer extends JFrame {

    // Top Menu
    private JMenuBar bar;
    private JMenu fileMenu;
    private JMenuItem aboutMenu;
    private JMenuItem exitMenu;

    // vars for countdown
    static JLabel countdownLabelClockMin;
    static JLabel countdownLabelMin;
    static JLabel countdownLabelClockSec;
    static JLabel countdownLabelSec;

    // Start Stop Buttons
    private JButton startButton;
    private JButton stopButton;
    static JLabel errorHandlingLabel;

    // Speaker Index/Time Storage
    static ArrayList<Integer> speakerIndexMin = new ArrayList<Integer>();
    static ArrayList<Integer> speakerIndexSec = new ArrayList<Integer>();
    static int selectedIndex;

    // Speakers
    static JLabel currentTalker;    
    
    // instance variables to load speaker list
    static JTextField browseToFileField;
    private JButton browseToFileButton;
    
    // vars for list
    static DefaultListModel listModel;
    private JList speakerList;
    private JScrollPane listScroller;
    private javax.swing.Timer errorLabelTimer;
    private boolean labelFadeTimer = false;
    
    static int selections[];
    static Object selectedValues[];
    
    // add/remove speakers
    static JTextField addRemoveSpeakerField;
    static JLabel addRemoveSpeakerFieldErrorLabel;
    private JButton addRemoveSpeakerFieldPlus;
    private JButton addRemoveSpeakerFieldMinus;
    
    // edit speechtime
    static JTextField editSpeechTimeField;
    private JButton editSpeechTimeButton;
    static JButton showHideClock;
    private static int timevalue;
    static JLabel errorHandlingLabelRight;
    static JCheckBox editSpeechCheckBox;
    
    // THE GUI base
    JPanel content = new JPanel();

    //constructor
    public WindowContainer (int timevalue) {
        // PANEL Layout
        setSize(800,620);
        setTitle("Speechtimer 1.0 (by monks.de)");
        setLocation(150,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(null);
        setContentPane(content);
        
	// Top Menu
        bar = new JMenuBar();
        setJMenuBar(bar);
        fileMenu = new JMenu("File");
        aboutMenu = new JMenuItem("About");
        exitMenu = new JMenuItem("Exit");
        bar.add(fileMenu);
        fileMenu.add(aboutMenu);
        fileMenu.add(exitMenu);
        exitMenu.addActionListener(new MenuSelection(1));
        aboutMenu.addActionListener(new MenuSelection(2));
        
        // Timer Clock
        // Minutes
        countdownLabelClockMin = new JLabel();
        countdownLabelClockMin.setText(Integer.toString(timevalue));
        countdownLabelMin = new JLabel("min");
        countdownLabelClockMin.setFont(new Font("Sans Serif", Font.BOLD, 52));
        content.add(countdownLabelClockMin);
        content.add(countdownLabelMin);
        countdownLabelClockMin.setBounds(30,185,80,100);
        countdownLabelMin.setBounds(110,198,80,100);

        // Seconds
        countdownLabelClockSec = new JLabel("0");
        countdownLabelSec = new JLabel("sec");
        countdownLabelClockSec.setFont(new Font("Sans Serif", Font.BOLD, 32));
        content.add(countdownLabelClockSec);
        content.add(countdownLabelSec);
        countdownLabelClockSec.setBounds(140,193,80,100);
        countdownLabelSec.setBounds(190,198,80,100);
        
        // StartStop Buttons
        startButton = new JButton("START");
        stopButton = new JButton("STOP");
        errorHandlingLabel = new JLabel("");
        content.add(startButton);
        content.add(stopButton);
        content.add(errorHandlingLabel);
        startButton.setBounds(30,425,85,30);
        stopButton.setBounds(130,425,85,30);
        errorHandlingLabel.setBounds(30,455,285,30);
        errorHandlingLabel.setForeground(Color.red);

        // Current Talker
        currentTalker = new JLabel("Prof. Kunz");
        content.add(currentTalker);
        currentTalker.setFont(new Font("Sans Serif", Font.BOLD, 44));
        currentTalker.setBounds(30,35,550,50);
       
	// Load Speaker List
        browseToFileField = new JTextField("Path to txt file with speakerlist");
        content.add(browseToFileField);
	browseToFileField.setBounds(30,500,205,30);
		
	browseToFileButton = new JButton("Browse");
        content.add(browseToFileButton);
        browseToFileButton.setBounds(248,500,85,30);
		
	// The List
        listModel = new DefaultListModel();
        listModel.addElement("John Doe");

        speakerList = new JList(listModel);
        speakerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        speakerList.setLayoutOrientation(JList.VERTICAL_WRAP);
        speakerList.setVisibleRowCount(-1);
        
        listScroller = new JScrollPane(speakerList);
        listScroller.setPreferredSize(new Dimension(260, 80));
        listScroller.setBounds(590,40,175,416);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        
        // add/remove speakers    
        addRemoveSpeakerField = new JTextField("Add a speaker");
        addRemoveSpeakerFieldErrorLabel = new JLabel();
        
        addRemoveSpeakerFieldPlus = new JButton();
        addRemoveSpeakerFieldPlus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speechtimer/resources/plus.png")));
        
        addRemoveSpeakerFieldMinus = new JButton();
        addRemoveSpeakerFieldMinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/speechtimer/resources/minus.png")));
        
        addRemoveSpeakerField.setBounds(590,484,119,26);
        addRemoveSpeakerFieldErrorLabel.setBounds(590,458,160,26);
        addRemoveSpeakerFieldPlus.setBounds(714,484,25,25);
        addRemoveSpeakerFieldMinus.setBounds(742,484,25,25);
        
        content.add(addRemoveSpeakerField);
        content.add(addRemoveSpeakerFieldErrorLabel);
        content.add(addRemoveSpeakerFieldPlus);
        content.add(addRemoveSpeakerFieldMinus);
        
        // edit speech time
        editSpeechTimeField = new JTextField(timevalue);
        content.add(editSpeechTimeField);
	editSpeechTimeField.setBounds(375,425,75,30);
		
	editSpeechTimeButton = new JButton("Set Time");
        content.add(editSpeechTimeButton);
        editSpeechTimeButton.setBounds(470,425,95,30);
        
        errorHandlingLabelRight = new JLabel("");
        content.add(errorHandlingLabelRight);
        errorHandlingLabelRight.setBounds(375,455,285,30);
        errorHandlingLabelRight.setForeground(Color.red);
        
        editSpeechCheckBox = new JCheckBox("Change all");
        content.add(editSpeechCheckBox);
        editSpeechCheckBox.setBounds(371,475,85,30);
        
        showHideClock = new JButton("Hide/Show");
        content.add(showHideClock);
        showHideClock.setBounds(470,225,95,30);
                
        content.add(listScroller);
        

        // *************** LOGIC *************

        // Initialise SpeakerList Arraylist
        speakerIndexMin.add(timevalue);
        speakerIndexSec.add(0);
        selectedIndex = 0;

        // Event listeners for Buttons
        startButton.addActionListener(new ButtonPressed("start", content));
        stopButton.addActionListener(new ButtonPressed("stop", content));
        browseToFileButton.addActionListener(new ButtonPressed("browse", content));
        editSpeechTimeButton.addActionListener(new ButtonPressed("setTime", content));
        showHideClock.addActionListener(new ButtonPressed("toggleClock", content));
        addRemoveSpeakerFieldPlus.addActionListener(new ButtonPressed("addSpeaker", content));
        addRemoveSpeakerFieldMinus.addActionListener(new ButtonPressed("removeSpeaker", content)); 
        
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
              public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (!ButtonPressed.countdownRunning) {

                    boolean adjust = listSelectionEvent.getValueIsAdjusting();
                    System.out.println(", Adjusting? " + adjust);
                    if (!adjust) {
                          JList list = (JList) listSelectionEvent.getSource();
                          selections = list.getSelectedIndices();
                          selectedValues = list.getSelectedValues();

                          // debugging
                          for (int i = 0, n = selections.length; i < n; i++) {
                                if (i == 0) {
                                  System.out.println("  Selections: ");
                                }
                                System.out.println(selections[i] + "/" + selectedValues[i] + " ");

                                // Changing GUI
                                String selectedValue = list.getSelectedValue().toString();
                                currentTalker.setText(selectedValue);
                                addRemoveSpeakerField.setText(selectedValue);

                                selectedIndex = list.getSelectedIndex();

                                // Loading the time into the GUI
                                WindowContainer.countdownLabelClockMin.setText(speakerIndexMin.get(selectedIndex).toString());
                                WindowContainer.countdownLabelClockSec.setText(speakerIndexSec.get(selectedIndex).toString());
                                // debugging
                                // System.out.println(selectedIndex);
                          }
                    }
                }

                // UI Feedback / Usability

                else {
                    Color customColor = new Color(178,34,34);
                    WindowContainer.errorHandlingLabel.setForeground(customColor);
                    WindowContainer.errorHandlingLabel.setText("Please stop the timer first.");

                    if (!labelFadeTimer) {
                        // let the error message disappear again
                        errorLabelTimer = new javax.swing.Timer(2500, null);
                        errorLabelTimer.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                 errorHandlingLabel.setText("");
                                 errorLabelTimer.stop();
                                 labelFadeTimer = false;
                          }
                       });
                       errorLabelTimer.start();
                       labelFadeTimer = true;
                    }
                }
            }
        };speakerList.addListSelectionListener(listSelectionListener);
    }           
}
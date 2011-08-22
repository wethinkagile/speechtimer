package speechtimer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    private JLabel countdownLabelMin;
    static JLabel countdownLabelClockSec;
    private JLabel countdownLabelSec;

    // Start Stop Buttons
    private JButton startButton;
    private JButton stopButton;
    static JLabel errorHandlingLabel;

    // Speaker Index/Time Storage
    static ArrayList speakerIndexMin = new ArrayList();
    static ArrayList speakerIndexSec = new ArrayList();
    static int selectedIndex;

    // Speakers
    private JLabel currentTalker;    
    
    // instance variables to load speaker list
    static JTextField browseToFileField;
    private JButton browseToFileButton;
    
    // vars for list
    static DefaultListModel listModel;
    private JList speakerList;
    private JScrollPane listScroller;
    private javax.swing.Timer errorLabelTimer;
    private boolean labelFadeTimer = false;
    
    JPanel content = new JPanel();

    //constructor
    public WindowContainer () {
        // PANEL Layout
        setSize(800,620);
        setTitle("SpeakClock 0.1 beta");
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
        countdownLabelClockMin = new JLabel("60");
        countdownLabelMin = new JLabel("min");
        countdownLabelClockMin.setFont(new Font("Sans Serif", Font.BOLD, 52));
        content.add(countdownLabelClockMin);
        content.add(countdownLabelMin);
        countdownLabelClockMin.setBounds(30,185,80,100);
        countdownLabelMin.setBounds(110,198,80,100);

        // Seconds
        countdownLabelClockSec = new JLabel("60");
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
	browseToFileField.setBounds(30,500,250,30);
		
	browseToFileButton = new JButton("Browse");
        content.add(browseToFileButton);
        browseToFileButton.setBounds(300,500,85,30);
		
	// The List
        listModel = new DefaultListModel();
        listModel.addElement("John Doe");

        speakerList = new JList(listModel);
        speakerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        speakerList.setLayoutOrientation(JList.VERTICAL_WRAP);
        speakerList.setVisibleRowCount(-1);
        
        listScroller = new JScrollPane(speakerList);
        listScroller.setPreferredSize(new Dimension(260, 80));
        listScroller.setBounds(590,40,175,490);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        
        content.add(listScroller);
        

        // *************** LOGIC *************

        // Initialise SpeakerList Arraylist
        speakerIndexMin.add("15");
        speakerIndexSec.add("59");
        selectedIndex = 0;

        // Event listeners for Buttons
        startButton.addActionListener(new ButtonPressed("start", content));
        stopButton.addActionListener(new ButtonPressed("stop", content));
        browseToFileButton.addActionListener(new ButtonPressed("browse", content));
        
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
              public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (!ButtonPressed.countdownRunning) {

                    boolean adjust = listSelectionEvent.getValueIsAdjusting();
                    System.out.println(", Adjusting? " + adjust);
                    if (!adjust) {
                          JList list = (JList) listSelectionEvent.getSource();
                          int selections[] = list.getSelectedIndices();
                          Object selectedValues[] = list.getSelectedValues();

                          // debugging
                          for (int i = 0, n = selections.length; i < n; i++) {
                                if (i == 0) {
                                  System.out.println("  Selections: ");
                                }
                                System.out.println(selections[i] + "/" + selectedValues[i] + " ");

                                // Changing GUI
                                String selectedValue = list.getSelectedValue().toString();
                                currentTalker.setText(selectedValue);

                                // Reading the selected Index and its time from the Arraylist speakerIndex
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

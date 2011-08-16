package speechtimer;

import javax.swing.*;

import java.util.Timer;
import java.awt.Font;

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

    // Imminent Talkers
    private JLabel previousTalker;
    private JLabel currentTalker;
    private JLabel nextTalker;
    
    // instance variables to load speaker list
    private JTextField browseToFileField;
    private JButton browseToFileButton;
    
    // vars for list
    private JTextField speakerList;
    
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
        countdownLabelClockMin.setBounds(30,15,80,100);
        countdownLabelMin.setBounds(110,28,80,100);

        // Seconds
        countdownLabelClockSec = new JLabel("60");
        countdownLabelSec = new JLabel("sec");
        countdownLabelClockSec.setFont(new Font("Sans Serif", Font.BOLD, 32));
        content.add(countdownLabelClockSec);
        content.add(countdownLabelSec);
        countdownLabelClockSec.setBounds(140,23,80,100);
        countdownLabelSec.setBounds(190,28,80,100);
        
        // StartStop Buttons
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        content.add(startButton);
        content.add(stopButton);
        startButton.setBounds(30,130,85,30);
        stopButton.setBounds(130,130,85,30);
        
        // Imminient Talkers
        previousTalker = new JLabel("Dr. Hinz");
        currentTalker = new JLabel("Prof. Kunz");
        nextTalker = new JLabel("Mustermann (AIP)");
        
        content.add(previousTalker);
        content.add(currentTalker);
        content.add(nextTalker);
        
        previousTalker.setFont(new Font("Sans Serif", Font.BOLD, 22));
        currentTalker.setFont(new Font("Sans Serif", Font.BOLD, 44));
        nextTalker.setFont(new Font("Sans Serif", Font.BOLD, 22));
           
        previousTalker.setBounds(30,180,285,40);
        currentTalker.setBounds(30,220,285,40);
        nextTalker.setBounds(30,260,285,40);

	// Load Speaker List
        browseToFileField = new JTextField("Path to txt file with speakerlist");
        content.add(browseToFileField);
	browseToFileField.setBounds(30,500,250,30);
		
	browseToFileButton = new JButton("Browse");
        content.add(browseToFileButton);
        browseToFileButton.setBounds(300,500,85,30);
		
	// The List
        speakerList = new JTextField();
        content.add(speakerList);
        speakerList.setBounds(470,40,260,490);
        // speakerList.setCaretPosition();
        /*
         *  static JTextArea area = new JTextArea(5, 20);
         * private JScrollPane scrollPane = new JScrollPane(area);
         * private JLabel scrollPaneLabel = new JLabel("Program Output");
         * WindowContainer.area.append(bash_str + newline);
         * WindowContainer.area.setCaretPosition(WindowContainer.area.getDocument().getLength());
         */
	}
}

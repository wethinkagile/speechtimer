package speechtimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JPanel;

public class EncodingDialog extends JDialog {

    private JLabel line0;
    private JLabel line1;
    private JLabel line2;
    private JLabel line3;

  public EncodingDialog(JFrame parent) {
    super(parent, "Encoding Switched", true);

    Box b = Box.createVerticalBox();
    b.add(Box.createGlue());
    line0 = new JLabel("\t \t \t \t \t \t \t \t \t \t \t \t \t \t Successfully switched to " + ButtonPressed.getEncoding());
    line1 = new JLabel("\t \t \t \t \t \t \t \t \t \t Now please try to reload the external file.");
    b.add(line0);
    b.add(line1);

    b.add(Box.createGlue());
    getContentPane().add(b, "Center");
    JPanel p2 = new JPanel();
    JButton ok = new JButton("OK");
    p2.add(ok);
    getContentPane().add(p2, "South");

    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        setVisible(false);
      }
    });
    setLocation(400,350);
    setSize(300, 150);
  }
}

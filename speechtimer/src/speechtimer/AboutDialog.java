package speechtimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {

    private JLabel line0;
    private JLabel line1;
    private JLabel line2;
    private JLabel line3;

  public AboutDialog(JFrame parent) {
    super(parent, "About Speechtimer", true);

    Box b = Box.createVerticalBox();
    b.add(Box.createGlue());
    line0 = new JLabel(" ");
    line1 = new JLabel("\t \t \t This program is licensed for monks.de under the GNU General Public License v3 of the FSF");
    line2 = new JLabel("\t \t \t \t \t \t \t \t \t \t This software must remain free to distribute copies and open to read the source.");
    b.add(line0);
    b.add(line1);
    b.add(line2);
    line3 = new JLabel("\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t CC-BY-SA Stephan Kristyn / Monks GmbH 2011");
    b.add(line3);

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
    setSize(600, 185);
  }
}

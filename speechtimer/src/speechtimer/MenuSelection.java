package speechtimer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author nottinhill
 */
public class MenuSelection implements ActionListener {
   
   private int menuItem;
   
   public MenuSelection (int menuItem) {
       this.menuItem = menuItem;
   }
    
    public void actionPerformed (ActionEvent e) {
        
        if (menuItem == 1){
            // this should be changed to something less drastic..
            System.exit(0);
        }
        
        else if (menuItem == 2) {
            // WindowContainer.area.append("To show your appreciation for this \n tool please send Bitcoins to 18QeVng1ArbTBoyFTXGwk78caQgDESUC4v" + "\n");
            // WindowContainer.area.setCaretPosition(WindowContainer.area.getDocument().getLength());
        }
    }
   
}

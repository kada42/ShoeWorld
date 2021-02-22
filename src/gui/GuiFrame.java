package gui;

import controller.Controller;

import javax.swing.*;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-11 <br>
 * Time: 17:08 <br>
 * Project: ShoeWorld <br>
 */
public class GuiFrame extends JFrame {

    Window window = new Window();
    Controller c;

    public GuiFrame(){
        c = new Controller(window);
        this.add(window);

        int membershipNr;
        String password;

        while(true){
            try {
                String input = JOptionPane.showInputDialog("Ange erat medlemsnummer:");
                if(input == null) System.exit(1337);
                membershipNr = Integer.parseInt(input.trim());
                password = JOptionPane.showInputDialog("Ange erat lösenord:");
                if(password == null) System.exit(1337);
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Ni måste ange medlemsnummer med siffror.");
                continue;
            }
            if(c.checkCredentials(membershipNr,password)) {
                c.setMembershipNr(membershipNr);
                c.setTitleName();
                break;
            }
            else JOptionPane.showMessageDialog(null,"Ni angav antingen fel medlemsnummer eller fel lösenord.");
        }
        setUpFrame();
    }

    public void setUpFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new GuiFrame();
    }
}

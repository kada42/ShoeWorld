package GUI;

import javax.swing.*;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-11 <br>
 * Time: 17:08 <br>
 * Project: ShoeWorld <br>
 */
public class GuiFrame extends JFrame {

    Window window = new Window();

    public GuiFrame(){
        this.add(window);
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

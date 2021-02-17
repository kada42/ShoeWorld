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

        // Inlogg via JOption Pane

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

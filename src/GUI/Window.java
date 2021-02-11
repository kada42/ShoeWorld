package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Tomas Dahlander <br>
 * Date: 2021-02-11 <br>
 * Time: 16:50 <br>
 * Project: ShoeWorld <br>
 */
public class Window extends JPanel {

    // Panels
    private final JPanel east = new JPanel();
    private final JPanel northWest = new JPanel(new GridLayout(3,1));
    private final JPanel southWest = new JPanel();

    // Textareas
    private final JTextArea infoWindow = new JTextArea();
    private final JScrollPane sp = new JScrollPane(infoWindow);

    // Buttons
    private final JButton allShoes = new JButton("View all shoes");
    private final JButton allCategories = new JButton("View with categories");

    // Textfields
    private final JTextField articleNrField = new JTextField(20);

    public Window(){
        this.setLayout(new BorderLayout());
        addPanels();
        setUpEastSide();
        setUpSouthBar();
        setUpNorthWestSide();
    }

    private void addPanels(){
        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        this.add(centerPanel,BorderLayout.CENTER);

        JPanel west = new JPanel(new GridLayout(2,1));
        centerPanel.add(west);
        centerPanel.add(east);

        west.add(northWest);
        west.add(southWest);

        east.setBackground(Color.BLUE);
        northWest.setBackground(Color.GREEN);
        southWest.setBackground(Color.RED);
    }

    private void setUpEastSide(){
        sp.setPreferredSize(new Dimension(400,700));
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        east.add(sp);
    }

    private void setUpNorthWestSide(){
        JLabel placeOrder = new JLabel("Place order");
        placeOrder.setFont(new Font(Font.SERIF,Font.BOLD,20));
        northWest.add(placeOrder);

        JPanel panel1 = new JPanel();
        JLabel articleNr = new JLabel("Article nr");
        panel1.add(articleNr, CENTER_ALIGNMENT);
        panel1.add(articleNrField);
        northWest.add(panel1);


    }

    private void setUpSouthBar(){
        JPanel panel = new JPanel();
        panel.add(allShoes);
        panel.add(allCategories);
        this.add(panel, BorderLayout.SOUTH);
    }

}
